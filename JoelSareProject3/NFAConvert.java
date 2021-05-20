import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class NFAConvert
{
    public static void main(String[] args)
    {
        NFA nfa = new NFA();
        DFA dfa = new DFA();

        ArrayList<String> input = new ArrayList<String>();
        try
        {
            String s = args[0];
            Scanner in = new Scanner(new File(s));
            while (in.hasNextLine()) {
                input.add(in.nextLine());
            }
            in.close();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("NFAConvert: no input files specified");
            System.exit(0);
        }
        catch (FileNotFoundException f)
        {
            System.out.printf("NFAConvert: the file '%s' could not be opened\n", args[0]);
            System.exit(0);
        }
        nfa = initializeNFA(input);

        int states = nfa.qSize();

        String[] closureTable = new String[states];
        ArrayList<String> transTable = nfa.transitionArray();

        E(nfa, closureTable);
        popDFATransitions(dfa, nfa, closureTable, transTable);
        popDFAFinal(dfa, nfa);

        dfa.makePretty();
        dfa.printDFA();
    }

    public static void E(NFA nfa, String [] closureTable)
    {
        String curState = "";
        String newState;
        for (int i = 0; i < nfa.qSize(); i++)
        {
            curState = nfa.getQ(i);
            closureTable[i] = curState + ",";
        }

        Stack<String> a = new Stack<String>();
        for (int i = 0; i < nfa.qSize(); i++)
        {
            a = new Stack<String>();
            newState = eHelper(nfa, closureTable[i], a);

            if (!closureTable[i].contains(newState))
            {
                closureTable[i] += newState;
            }
        }
    }

    public static String eHelper(NFA nfa, String curState, Stack<String> a)
    {
        String [] words;
        String [] state = curState.split(",");
        String result = new String();
        for (String st : state)
        {
            a.push(st);
            for (int i = 0; i < nfa.deltaSize(); i++)
            {
                words = nfa.getDelta(i).split("\\s+");
                if (words[0].equals(st) && words[1].equals("E"))
                {
                    if (!a.contains(words[2]))
                    {
                        a.push(words[2]);
                        result += words[2] + "," + eHelper(nfa, words[2], a);
                    }
                }
            }
        }
            
        return result;
    }

    public static void popDFAFinal(DFA dfa, NFA nfa) {
        ArrayList<String> DFAstates = dfa.getQ();
        ArrayList<String> NFAfinalStates = nfa.getF();

        for (String st : DFAstates) {
            if (contains(st, NFAfinalStates) != -1)
                dfa.addF(st);
        }
    }

    public static void popDFATransitions(DFA dfa, NFA nfa, String[] closureTable, ArrayList<String> transTable) {
        String start = closureTable[(nfa.getIndexQ(nfa.getq0()))];
        dfa.cloneq0(start);
        dfa.addQ(dfa.getq0());
        dfa.cloneSigma(nfa.getSigma());
        String[] Q;
        String[] words;
        String[] closStates;
        String newState = "";
        int ind;
        for (int i = 0; i < dfa.getQSize(); i++) {
            for (int j = 0; j < dfa.getSigmaSize(); j++) {
                Q = dfa.getQ(i).split(",");
                for (String state : Q) {
                    for (int trans = 0; trans < transTable.size(); trans++) {
                        words = transTable.get(trans).split("\\s+");
                        if (words[0].equals(state) && words[1].equals(dfa.getSigma(j)))
                        {
                            closStates = closureTable[nfa.getIndexQ(words[2])].split(",");
                            for (String st : closStates) {
                                if (!newState.contains(st))
                                    newState += st + ",";
                            }
                        }
                    }
                }
                if (newState.equals(""))
                {
                    newState = "∅";
                }
                if (alreadyHas(newState.replace(",", ""), dfa.getQ()))
                {
                    String newSortedState = sortString(newState);
                    String curQSorted = sortString(dfa.getQ(i));
                    if (newSortedState.equals(curQSorted))
                        dfa.addDelta(String.format("%s %s %s", dfa.getQ(i), dfa.getSigma(j), dfa.getQ(i)));
                    else
                        dfa.addDelta(String.format("%s %s %s", dfa.getQ(i), dfa.getSigma(j), newState));
                } 
                else
                {
                    dfa.addQ(newState);
                    dfa.addDelta(String.format("%s %s %s", dfa.getQ(i), dfa.getSigma(j), newState));
                }
                newState = "";
            }
        }
    }

    public static String sortString(String inputString) 
    {
        char tempArray[] = inputString.toCharArray(); 
        Arrays.sort(tempArray); 
        return new String(tempArray); 
    }

    public static boolean alreadyHas(String a, ArrayList<String> b)
    {
        a = sortString(a);
        for (String string : b)
        {
            string = string.replace(",", "");
            string = sortString(string);
            if (string.equals(a))
            {
                return true;
            }
        }
        return false;
    }

    /*
    * Returns index of DFA %Q that is basically the same as "a"
    * if nothing like "a" exists in %Q, return -1
    */
    public static int contains(String a, ArrayList<String> b)
    {
        for (int i = 0; i < b.size(); i++)
        {
            if (isSubset(a, b.get(i)))
            {
                return i;
            }
        }
        return -1;
    }

    /*
    *  Checks if a is subset to (or equal to) b
    */
    public static boolean isSubset(String a, String b)
    {
        String [] aArr = a.split(",");
        for (String aString : aArr)
        {
            if(b.contains(aString))
            {
                return true;
            }
        }
        return false;
    }

    public static NFA initializeNFA(ArrayList<String> input)
    {
        int currentString = 1;
        NFA temp = new NFA();
        while (!input.get(currentString).equals("% Sigma"))
        {
            temp.addQ(input.get(currentString++));
        }
        currentString++;
        while (!input.get(currentString).equals("% F"))
        {
            temp.addSigma(input.get(currentString++));
        }
        currentString++;
        while (!input.get(currentString).equals("% Q0"))
        {
            temp.addF(input.get(currentString++));
        }
        currentString++;
        while (!input.get(currentString).equals("% Delta"))
        {
            temp.updateq0(input.get(currentString++));
        }
        currentString++;
        while (currentString < input.size())
        {
            temp.addDelta(input.get(currentString++));
        }
        return temp;
    }
}
