package Expressions;

import RelationalAlgebra.Header;
import RelationalAlgebra.Primitive;
import RelationalAlgebra.Tuple;

/**
 * Created by Булат on 21.11.2015.
 */
public abstract class Condition {
    Header header;
    Tuple tuple;
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
    public abstract boolean expr();
    public boolean match(Header header, Tuple tuple) {
        this.header = header;
        this.tuple = tuple;
        return header.conform(tuple) && expr();
    }
}
