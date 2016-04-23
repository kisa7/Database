package Expressions;

import RelationalAlgebra.Header;
import RelationalAlgebra.Primitive;
import RelationalAlgebra.Tuple;

/**
 * Created by Булат on 21.11.2015.
 */
public abstract class Expression {
    Header header;
    Tuple tuple;
    public static enum Order{
        ASC, DESC;
    }
    public Primitive get(String name)
    {
        for (int i = 0; i < header.getColumns().size(); i++)
        {
            if (header.getColumns().get(i).getName().equals(name))
            {
                return tuple.get(i);
            }
        }
        return null;
    }
    public abstract String name();
    public Primitive calc(Header header, Tuple tuple)
    {
        this.header = header;
        this.tuple = tuple;
        return expr();
    }
    public abstract Primitive expr();
}
