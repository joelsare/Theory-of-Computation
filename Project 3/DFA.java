import java.util.ArrayList;

public class DFA
{
    ArrayList<String> Q;
    ArrayList<String> sigma;
    ArrayList<String> F;
    String q0;
    ArrayList<String> delta;

    //create DFA object
    public DFA()
    {
        Q = new ArrayList<String>();
        sigma = new ArrayList<String>();
        F = new ArrayList<String>();
        q0 = "";
        delta = new ArrayList<String>();
    }

    void cloneQ(ArrayList<String> newQ)
    {
        Q = newQ;
    }

    void addQ(String a)
    {
        Q.add(a);
    }

    public String getQ(int i)
    {
        return Q.get(i);
    }

    public int getQSize()
    {
        return Q.size();
    }

    public ArrayList<String> getQ()
    {
        return Q;
    }

    String getq0()
    {
        return q0;
    }

    void cloneSigma(ArrayList<String> newSigma)
    {
        sigma = newSigma;
    }

    public int getSigmaSize()
    {
        return sigma.size();
    }

    public String getSigma(int i)
    {
        return sigma.get(i);
    }

    void cloneF(ArrayList<String> newF)
    {
        F = newF;
    }

    public void addF(String s)
    {
        F.add(s);
    }

    void cloneq0(String newq0)
    {
        q0 = newq0;
    }

    void cloneDelta(ArrayList<String> newDelta)
    {
        delta = newDelta;
    }

    public void addDelta(String s)
    {
        delta.add(s);
    }

    public String getDelta(int i)
    {
        return delta.get(i);
    }

    public void makePretty()
    {
        for (int i = 0; i < Q.size(); i++)
        {
            if (!Q.get(i).equals("∅"))
                Q.set(i, "{" + Q.get(i).substring(0, Q.get(i).length()-1) + "}");
        }
        for (int i = 0; i < F.size(); i++)
        {
            F.set(i, "{" + F.get(i).substring(0, F.get(i).length()-1) + "}");
        }
        q0 = "{" + q0.substring(0, q0.length()-1) + "}";
        for (int i = 0; i < delta.size(); i++)
        {
            String newDelta = "";
            String [] words = getDelta(i).split("\\s+");
            if (!words[0].equals("∅"))
                newDelta += "{" + words[0].substring(0, words[0].length()-1) + "}";
            else
                newDelta += "∅";

            newDelta += " " + words[1] + " ";

            if (!words[2].equals("∅"))
                newDelta += "{" + words[2].substring(0, words[2].length()-1) + "}";
            else
                newDelta += "∅";
            
            delta.set(i, newDelta);
        }
    }

    void printDFA()
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