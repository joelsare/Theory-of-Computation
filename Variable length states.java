import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NFAConvert
{
    public static void main(String[]args)
    {
        NFA nfa = new NFA();
        DFA dfa = new DFA();

        ArrayList<String> input = new ArrayList<String>();
        try
        {            
            String s =  args[0];
            Scanner in = new Scanner(new File(s));
            while (in.hasNextLine())
            {
                input.add(in.nextLine());
            }
            in.close();
        }
        catch (ArrayIndexOutOfBoundsException e) 
        {
            System.out.println("DFACheckBasic: no input files specified");
            System.exit(0);
        }
        catch (FileNotFoundException f)
        {
            System.out.printf("DFACheckBasic: the file '%s' could not be opened\n", args[0]);
            System.exit(0);
        }
        nfa = initializeNFA(input);

        int states = nfa.qSize();
        int sigSize = nfa.sigmaSize();

        int [] closureArray = new int [states];
        String [] closureTable = new String[states];

        popClosureTbl(closureArray, closureTable, nfa, dfa);

        for (int i = 0; i < closureTable.length; i++)
        {
            System.out.printf("Closure of %s: %s\n", nfa.getQ(i),closureTable[i]);
        }

        int s = 1;
        int i = 1;
        int startIndex = 1;
    }

    public static void mark(int closureArray[], String buf, NFA nfa) //check
    {
        int j;
        String [] state = buf.split(",");
        for(int i = 0; i < state.length; i++)
        {
            j = nfa.getIndexQ(state[i]);
            closureArray[j]++;
        }
    }

    public static int closure(int array[])
    {
        for(int i = 0; i < array.length; i++)
        {
            if (array[i] == 1)
            {
                return i;
            }
        }
        return -1;
    }

    public static String state(int closureArr[], String buffer, NFA nfa)
    {
        for(int j = 0; j < nfa.qSize(); j++)
        {
            if (closureArr[j] != 0)
            {
                buffer += nfa.getQ(j) + ",";
            }
        }
        return buffer;
    }

    public static void popClosureTbl(int closureArr[], String closeTbl[], NFA nfa, DFA dfa)
    {            
        String buffer;
        String [] a = nfa.closureArray();
        for (int i = 0; i < nfa.qSize(); i++)
        {
            for(int j = 0; j < closureArr.length; j++)
                closureArr[j] = 0;

            closureArr[i] = 2;

            buffer = a[i];
            mark(closureArr, buffer, nfa);
            int z = closure(closureArr);

            while (z != -1)
            {
                buffer = a[z];
                mark(closureArr, buffer, nfa);
                closureArr[z]++;
                z = closure(closureArr);
            }
            buffer = state(closureArr, buffer, nfa);
            closeTbl[i] = buffer.substring(1);
        }
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
