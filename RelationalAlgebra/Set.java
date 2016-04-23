package RelationalAlgebra;

import Expressions.Condition;
import Expressions.Expression;
import Index.BTree;
import Storage.Address;
import Storage.Table;

import java.util.ArrayList;

/**
 * Created by Наська on 21.11.2015.
 */
public abstract class Set {
    public abstract boolean add(Tuple t);
    public abstract RAMSet limit(int n);
    public abstract RAMSet all();
    @Override
    public String toString() {
        RAMSet s = limit(100);
        int numOfAttributes = s.getHeader().getColumns().size();
        Integer[] maxLength = new Integer[numOfAttributes];
        String resString = "";
        for(int i = 0; i < s.getHeader().getColumns().size(); i++) {
            String name = s.getHeader().getColumns().get(i).getName();
            resString += name;
            maxLength[i] = s.findMaxWordLength(i);
            for(int j = name.length(); j <= maxLength[i]; j++) {
                if(j == maxLength[i]) {
                    resString += "|";
                }
                else {resString += " ";}
            }
        }
        resString += "\n";
        for(int i = 0; i < s.getTuples().size(); i++) {
            resString += s.getTuples().get(i).showTuple(maxLength);
        }
        return resString;
    }
    public abstract RAMSet order(Expression[] expressions, Expression.Order[] orders, int limit);
    public RAMSet sort(Expression[] expressions, Expression.Order[] orders) {
        RAMSet set = all();
        Tuple tmp;
        Primitive p1;
        Primitive p2;
        for(int k = 0; k < set.getTuples().size() - 1; k++) {
            for (int i = 0; i < set.getTuples().size() - k - 1; i++) {
                int j = 0;
                Tuple tuple1 = set.getTuples().get(i);
                Tuple tuple2 = set.getTuples().get(i + 1);
                p1 = expressions[j].calc(set.getHeader(), tuple1);
                p2 = expressions[j].calc(set.getHeader(), tuple2);
                while (p1.equals(p2) && j + 1 < expressions.length) {
                    j++;
                    p1 = expressions[j].calc(set.getHeader(), tuple1);
                    p2 = expressions[j].calc(set.getHeader(), tuple2);
                }
                if (p1.greater(p2) && orders[j] == Expression.Order.ASC ||
                        p2.greater(p1) && orders[j] == Expression.Order.DESC) {
                    tmp = tuple1;
                    set.getTuples().set(i, tuple2);
                    set.getTuples().set(i + 1, tmp);
                }
            }
        }
        return set;
     }
    public abstract Header getHeader();
    public RAMSet join(Table t, Expression c, String f2, Condition condition)
    {
        RAMSet s = all();
        RAMSet res = new RAMSet(getHeader().union(t.getHeader()));
        BTree<Primitive, Address> index = t.getIndex(f2);
        Header h = t.getHeader();
        if (index == null)
        {
            return s;
        }
        for (Tuple tp: s.getTuples())
        {
            Primitive p = c.calc(s.getHeader(), tp);
            for (Address address: index.search(p))
            {
                res.add(tp.union(t.getDB().loadTuple(address, h)));
            }
        }
        return res;
    }
    public abstract RAMSet selection(Condition condition);
    public String getJson()
    {
        StringBuilder str = new StringBuilder("{  ");
        RAMSet s = all();
        int j = 0;
        for (Tuple t: s.getTuples())
        {
            str.append("\"");
            str.append(j++);
            str.append("\":");
            str.append("{");
            for (int i = 0; i < getHeader().getColumns().size(); i++)
            {
                str.append("\"");
                str.append(getHeader().getColumns().get(i).getName());
                str.append("\" : ");
                str.append(t.get(i).getDisplayedValue());
                str.append(", ");
            }
            str.delete(str.length() - 2, str.length());
            str.append("} ,");
        }
        str.delete(str.length() - 2, str.length());
        str.append("}");
        return str.toString();
    }
    public RAMSet projection(Expression[] expressions) {
        RAMSet set = all();
        Primitive p;
        Tuple tuple;
        Column column;
        Header header;
        Primitive.Type[] types = new Primitive.Type[expressions.length];
        Column[] columns = new Column[expressions.length];
        ArrayList<Tuple> tuples = new ArrayList<Tuple>();
        for(int i = 0; i < set.getTuples().size(); i++) {
            tuple = new Tuple();
            for(int j = 0; j < expressions.length; j++) {
                p = expressions[j].calc(set.getHeader(), set.getTuples().get(i));
                if(i == 0) {
                    types[j] = p.getType();
                }
                tuple.add(p);
            }
            tuples.add(tuple);
        }
        for(int i = 0; i < expressions.length; i++) {
            column = new Column(expressions[i].name(), types[i]);
            columns[i] = column;
        }
        header = new Header(columns);
        set = new RAMSet(header);
        set.setTuples(tuples);
        return set;
    }
}
