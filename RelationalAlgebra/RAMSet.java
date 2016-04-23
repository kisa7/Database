package RelationalAlgebra;

import Expressions.Condition;
import Expressions.Expression;

import java.util.ArrayList;

/**
 * Created by ����� on 20.11.2015.
 */
public class RAMSet extends Set{
    Header header;
    ArrayList<Tuple> tuples;
    public RAMSet(Header header)
    {
        this.header = header;
        this.tuples = new ArrayList<Tuple>();
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public ArrayList<Tuple> getTuples() {
        return tuples;
    }

    public void setTuples(ArrayList<Tuple> tuples) {
        this.tuples = tuples;
    }

    public boolean add(Tuple t)
    {
        if (header.conform(t))
        {
            this.tuples.add(t);
            return true;
        }
        return false;
    }
    public RAMSet all()
    {
        return this;
    }

    @Override
    public RAMSet order(Expression[] expressions, Expression.Order[] orders, int limit) {
        return sort(expressions, orders).limit(limit);
    }

    @Override
    public RAMSet selection(Condition condition) {
        ArrayList<Tuple> resTuples = new ArrayList<Tuple>();
        RAMSet set;
        for(int i = 0; i < tuples.size(); i++) {
            if(condition.match(getHeader(), getTuples().get(i))) {
                resTuples.add(getTuples().get(i));
            }
        }
        set = new RAMSet(header);
        set.setTuples(resTuples);
        return set;
    }

    public RAMSet limit(int n) {
        RAMSet set = new RAMSet(getHeader());
        if (n > tuples.size()) {
            return this;
        }
        for(int i = 0; i < n; i++) {
            set.getTuples().add(getTuples().get((i)));
        }
        return set;
    }

    public int findMaxWordLength(int i) {
        int maxLength = header.getColumns().get(i).getName().length();
        for(int j = 0; j < tuples.size(); j++) {
            int length = tuples.get(j).get(i).getDisplayedValue().length();
            if(length > maxLength) {
                maxLength = length;
            }
        }
        return maxLength;
    }


}
