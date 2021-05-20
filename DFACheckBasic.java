// Joel Sare

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DFACheckBasic
{
    public static void main(String[] args)
    {
        try
        {            
            String s = args[0];
            ArrayList<String> a = new ArrayList<String>();
            Scanner in = new Scanner(new File(s));
            
	    	while (in.hasNextLine())
            {
                a.add(in.nextLine());
            }
            in.close();
            
            for (String string : a)
            {
                char node = 'a';
                for (int i = 0; i < string.length(); i++)
                {
                    char c = string.charAt(i);
                    switch (node)
                    {
                        case 'a':
                            if (c == '0')
                            {
                                node = 'b';
                            }
                            else if (c == '1')
                            {
                                node = 'c';
                            }
                            break;
                        case 'b':
                            if (c == '0')
                            {
                                node = 'b';
                            }
                            else if (c == '1')
                            {
                                node = 'e';
                            }
                            break;
                        case 'c':
                            if (c == '0')
                            {
                                node = 'd';
                            }
                            else if (c == '1')
                            {
                                node = 'c';
                            }
                            break;
                        case 'd':
                            if (c == '0')
                            {
                                node = 'b';
                            }
                            else if (c == '1')
                            {
                                node = 'g';
                            }
                            break;
                        case 'e':
                            if (c == '0')
                            {
                                node = 'f';
                            }
                            else if (c == '1')
                            {
                                node = 'c';
                            }
                            break;
                        case 'f':
                            if (c == '0')
                            {
                                node = 'b';
                            }
                            else if (c == '1')
                            {
                                node = 'i';
                            }
                            break;
                        case 'g':
                            if (c == '0')
                            {
                                node = 'h';
                            }
                            else if (c == '1')
                            {
                                node = 'c';
                            }
                            break;
                        case 'h':
                            if (c == '0')
                            {
                                node = 'b';
                            }
                            else if (c == '1')
                            {
                                node = 'j';
                            }
                                break;
                        case 'i':
                            if (c == '0')
                            {
                                node = 'j';
                            }
                            else if (c == '1')
                            {
                                node = 'c';
                            }
                            break;
                        case 'j':
                            if (c == '0')
                            {
                                node = 'j';
                            }
                            else if (c == '1')
                            {
                                node = 'j';
                            }
                            break;
                    }
                }
                if (node != 'j')
                {
                    System.out.println(string + " accepted");
                }
                else
                {
                    System.out.println(string + " rejected");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) 
        {
            System.out.println("DFACheckBasic: no input file specified");
        }
        catch (FileNotFoundException f)
        {
            System.out.printf("DFACheckBasic: the file '%s' could not be opened\n", args[0]);
        }
    }
}