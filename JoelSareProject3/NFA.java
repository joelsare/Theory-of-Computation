import java.util.ArrayList;

public class NFA
{
    ArrayList<String> Q;
    ArrayList<String> sigma;
    ArrayList<String> F;
    String q0;
    ArrayList<String> delta;
    
    //Creates NFA object
    public NFA()
    {
        Q = new ArrayList<String>();
        sigma= new ArrayList<String>();
        F = new ArrayList<String>();
        delta = new ArrayList<String>();
    }

    public void addQ(String s)
    {
        Q.add(s);
    }

    public ArrayList<String> getSigma()
    {
        return sigma;
    }

    public String getq0()
    {
        return q0;
    }

    public void addSigma(String s)
    {
        sigma.add(s);
    }

    public void addF(String s)
    {
        F.add(s);
    }

    public ArrayList<String> getF()
    {
        return F;
    }

    public void updateq0(String s)
    {
        q0 = s;
    }

    public void addDelta(String s)
    {
        delta.add(s);
    }

    public int qSize()
    {
        return Q.size();
    }

    public String getDelta(int i)
    {
        return delta.get(i);
    }

    public String getQ(int i)
    {
        return Q.get(i);
    }

    public String[] closureArray()
    {
        String [] a = new String[qSize()];
        String eps = "";
        String [] words;
        String currState;
        for (int i = 0; i < qSize(); i ++)
        {
            currState = getQ(i);
            eps += currState;
            for(int j = 0; j < deltaSize(); j++)
            {
                words = getDelta(j).split("\\s+");
                if (words[0].equals(currState) && words[1].equals("E"))
                {
                    eps += "," + words[2];
                }
            }
            a[i] = eps;
            eps = "";
        }
        return a;
    }

    public ArrayList<String> transitionArray()
    {
        ArrayList<String> a = new ArrayList<String>();
        String [] words;
        for (int i = 0; i < deltaSize(); i ++)
        {
            words = getDelta(i).split("\\s+");
            if (!words[1].equals("E"))
            {
                a.add(String.format("%s %s %s", words[0],words[1],words[2]));
            }
        }
        return a;
    }

    public int getIndexQ(String s)
    {
        return Q.indexOf(s);
    }

    public int sigmaSize()
    {
        return sigma.size();
    }

    public int deltaSize()
    {
        return delta.size();
    }

    void printNFA()
    {
        int i;
        System.out.println("% Q");
        for(i = 0; i < Q.size(); i++)
            System.out.println(Q.get(i));

        System.out.println("% Sigma");
        for(i = 0; i < sigma.size(); i++)
            System.out.println(sigma.get(i));

        System.out.println("% F");
        for(i = 0; i < F.size(); i++)
            System.out.println(F.get(i));

        System.out.println("% Q0");
        System.out.println(q0);

        System.out.println("% Delta");
        for(i = 0; i < delta.size(); i++)
            System.out.println(delta.get(i));
    }
}