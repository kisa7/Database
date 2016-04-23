package RelationalAlgebra;

import Expressions.Expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Наська on 22.11.2015.
 */
public class OrderedRAMSetBuilder {
    int capacity;
    Expression[] expressions;
    Expression.Order[] orders;
    Header header;
    LinkedList<Tuple> tuples;

    public OrderedRAMSetBuilder(int capacity, Expression[] expressions, Expression.Order[] orders, Header header) {
        this.capacity = capacity;
        this.expressions = expressions;
        this.orders = orders;
        this.header = header;
        this.tuples = new LinkedList<Tuple>();
    }
    public void add(Tuple t)
    {
        if (t == null)
        {
            return;
        }
        if (tuples.isEmpty())
        {
            tuples.add(t);
            return;
        }
        ListIterator<Tuple> it = tuples.listIterator();
        Primitive p1;
        Primitive p2;
        int i = 0;
        for (i = 0; i < tuples.size(); i++)
        {
            int j = 0;
            Tuple tuple1 = t;
            Tuple tuple2 = it.next();
            p1 = expressions[j].calc(header, tuple1);
            p2 = expressions[j].calc(header, tuple2);
            while (p1.equals(p2) && j + 1 < expressions.length) {
                j++;
                p1 = expressions[j].calc(header, tuple1);
                p2 = expressions[j].calc(header, tuple2);
            }
            if (!(p1.greater(p2) && orders[j] == Expression.Order.ASC ||
                    p2.greater(p1) && orders[j] == Expression.Order.DESC)) {
                {
                    break;
                }
            }
        }
        if (i != 0)
        {
            tuples.add(i, t);
            if (capacity < tuples.size())
            {
                tuples.removeFirst();
            }
        }
        else if (capacity > tuples.size())
        {
            tuples.addFirst(t);
        }
    }
    public RAMSet get()
    {
        RAMSet set = new RAMSet(header);
        for (int i = tuples.size() - 1; i >= 0; i--)
        {
            set.add(tuples.get(i));
        }
        return set;
    }
}
