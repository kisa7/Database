package RelationalAlgebra;

import RelationalAlgebra.Primitives.RAInteger;
import RelationalAlgebra.Primitives.RAReal;
import RelationalAlgebra.Primitives.RAString;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ����� on 20.11.2015.
 */
public class Tuple {
    ArrayList<Primitive> primitives;
    public Tuple(Primitive[] primitives)
    {
        this.primitives = new ArrayList<Primitive>(Arrays.asList(primitives));
    }
    public Tuple(){this.primitives = new ArrayList<Primitive>();}
    public void add(Primitive p) { this.primitives.add(p);}
    public String toString()
    {
        StringBuilder str = new StringBuilder("(");
        for (Primitive a: primitives)
        {
            str.append(a.getDisplayedValue());
            str.append(", ");
        }
        str.delete(str.length() - 2, str.length());
        str.append(")");
        return str.toString();
    }

    public String showTuple(Integer[] maxLength) {
        String resString = "";
        for(int i = 0; i < primitives.size(); i++) {
            String value = primitives.get(i).getDisplayedValue();
            resString += value;
            for(int j = value.length(); j <= maxLength[i]; j++) {
                if (j == maxLength[i]) {
                    resString += "|";
                } else {
                    resString += " ";
                }
            }
        }
        resString += "\n";
        return resString;
    }

    public int size()
    {
        return primitives.size();
    }
    public Primitive get(int i)
    {
        return primitives.get(i);
    }
    public byte[] getDump()
    {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            for (Primitive p: this.primitives)
            {
                buf.write(p.getDump());
            }
            return buf.toByteArray();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public Tuple union(Tuple other)
    {
        Tuple t = new Tuple();
        for (Primitive p : primitives)
        {
            t.add(p);
        }
        for (int i = 0; i < other.size(); i++)
        {
            t.add(other.get(i));
        }
        return t;
    }
    public static Tuple fromByteStream(ByteArrayInputStream stream, Header h)
    {
        Tuple t = new Tuple();
        for (Column c: h.getColumns())
        {
            switch (c.getType())
            {
                case INT:
                    t.add(RAInteger.fromByteStream(stream));
                    break;
                case REAL:
                    t.add(RAReal.fromByteStream(stream));
                    break;
                case STRING:
                    t.add(RAString.fromByteStream(stream));
                    break;
            }
        }
        try {

            stream.close();
        } catch (Exception e)
        {

        }
        return t;
    }
}
