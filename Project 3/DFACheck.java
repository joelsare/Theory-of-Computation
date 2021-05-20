//Joel Sare

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DFACheck
{
    public static void main(String[] args)
        {
            String words[] = new String[3];
            ArrayList<String> dfa = new ArrayList<String>();;
            ArrayList<String> input = new ArrayList<String>();;
            try
            {            
                String s =  args[0];
                Scanner in = new Scanner(new File(s));
                
                while (in.hasNextLine())
                {
                    dfa.add(in.nextLine());
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
            }

            try
            {            
                String s = args[1];
                Scanner in = new Scanner(new File(s));
                
                while (in.hasNextLine())
                {
                    input.add(in.nextLine());
                }
                in.close();
            }
            catch (ArrayIndexOutOfBoundsException e) 
            {
                System.out.println("DFACheckBasic: invalid usage - the program must be given two files as input");
            }
            catch (FileNotFoundException f)
            {
                System.out.printf("DFACheckBasic: the file '%s' could not be opened\n", args[1]);
            }

            ArrayList<String> Q = new ArrayList<String>();
            ArrayList<String> sigma = new ArrayList<String>();
            ArrayList<String> F = new ArrayList<String>();
            String q0 = "";
            ArrayList<String> delta = new ArrayList<String>();
            int currentString = 1;

            while (!dfa.get(currentString).equals("% Sigma"))
            {
                Q.add(dfa.get(currentString++));
            }
            currentString++;
            while (!dfa.get(currentString).equals("% F"))
            {
                sigma.add(dfa.get(currentString++));
            }
            currentString++;
            while (!dfa.get(currentString).equals("% Q0"))
            {
                F.add(dfa.get(currentString++));
            }
            currentString++;
            while (!dfa.get(currentString).equals("% Delta"))
            {
                q0 = dfa.get(currentString++);
            }
            currentString++;
            while (currentString < dfa.size())
            {
                delta.add(dfa.get(currentString++));
            }

            for (String inputString : input)
            {
                String state = q0;
                for (int i = 0; i < inputString.length(); i++)
                {
                    for (String transition : delta)
                    {
                        words = transition.split("\\s+");
                        if (state.equals(words[0]) && inputString.charAt(i) == words[1].charAt(0))
                        {
                            state = words[2];
                            break;
                        }
                    }
                }
                /*
                if (F.contains(state))
                {
                    System.out.println(inputString + " accepted");
                }
                else
                {
                    System.out.println(inputString + " rejected");
                }
                */
            }
            dfaobj.cloneQ(Q);
            dfaobj.cloneSigma(sigma);
            dfaobj.cloneF(F);
            dfaobj.cloneq0(q0);
            dfaobj.cloneDelta(delta);
            dfaobj.printDFA();
        }
    }