package Expressions;

import RelationalAlgebra.Primitive;

/**
 * Created by Булат on 21.11.2015.
 */
public class ColumnExpression extends  Expression {
    private String name;
    public ColumnExpression(String name)
    {
        this.name = name;
    }
    @Override
    public String name() {
        return name;
    }

    @Override
    public Primitive expr() {
        return get(name);
    }
}
