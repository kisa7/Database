package RelationalAlgebra;

import RelationalAlgebra.Column;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ����� on 20.11.2015.
 */
public class Header {
    ArrayList<Column> columns;

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<Column> columns) {
        this.columns = columns;
    }

    public Header(Column[] columns) {
        this.columns = new ArrayList<Column>(Arrays.asList(columns));
    }
    public boolean conform (Tuple t)
    {
        if (this.columns.size() != t.size())
        {
            return false;
        }
        for (int i = 0; i < columns.size(); i++)
        {
            if (columns.get(i).getType() != t.get(i).getType())
            {
                return false;
            }
        }
        return true;
    }
    public void addColumn(Column c)
    {
        columns.add(c);
    }
    public Header union(Header h)
    {
        Header header = new Header(new Column[]{});
        for (Column c: getColumns())
        {
            header.addColumn(c);
        }
        for (Column c: h.getColumns())
        {
            header.addColumn(c);
        }
        return header;
    }
}
