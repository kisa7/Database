package Storage;

import RelationalAlgebra.Tuple;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
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
    RelationalAlgebra.Header toRA()
    {
        return new RelationalAlgebra.Header(columns.toArray(new RelationalAlgebra.Column[columns.size()]));
    }
    public byte[] getMetaData() {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            buf.write(ByteBuffer.allocate(4).putInt(columns.size()).array());
            for (Column c: columns) {
                buf.write(c.getMetaData());
            }
            return buf.toByteArray();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
