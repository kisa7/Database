package Storage;

import RelationalAlgebra.Primitive;
import RelationalAlgebra.Tuple;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tab;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Наська on 20.11.2015.
 */
public class Database {
    String name;
    ArrayList<Table> tables;
    RandomAccessFile f;
    int pageSize;
    int pagesNum;
    int tablesNum;
    long dataOffset;

    public Database(String name) {
        System.out.print("loading meta...");
        this.name = name;
        this.tables = new ArrayList<Table>();
        this.open();
        this.pageSize = this.readInt();
        this.pagesNum = this.readInt();
        this.tablesNum = this.readInt();
        for (int i = 0; i < this.tablesNum; i++) {
            String tableName = this.readDynamicString();
            long startPagePos = this.tell();
            int startPage = this.readInt();
            int curPage = this.readInt();
            int lastAutoincrement = this.readInt();
            int columnsNum = this.readInt();
            Column[] columns = new Column[columnsNum];
            for (int j = 0; j < columnsNum; j++)
            {
                String columnName = this.readDynamicString();
                Primitive.Type type = Primitive.Type.fromId(this.readByte());
                int isAutoIncrement = this.readByte();
                int isIndexed = this.readByte();
                Column c = new Column(columnName, type, isAutoIncrement == 1, isIndexed == 1);
                columns[j] = c;
            }
            Header header = new Header(columns);
            Table t = new Table(tableName, header, lastAutoincrement, startPage, curPage);
            t.setDb(this);
            t.setStartPagePos(startPagePos);
            this.tables.add(t);
        }
        dataOffset = this.tell();
        System.out.println("[OK]");
       // this.buildIndexes();
        System.out.print(dataOffset);
    }
    public byte[] readDynamicBlob()
    {
        try {
            int length = this.readInt();
            //System.out.println(length);
            byte[] bytes = new byte[length];
            f.read(bytes);
            return bytes;
        } catch (Exception e)
        {
            return null;
        }
    }
    public int getPageSize() {
        return pageSize;
    }
    public void writeBlob(byte[] b)
    {
        try
        {
            f.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeInt(int num)
    {
        try
        {
            f.write(ByteBuffer.allocate(4).putInt(num).array());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeByte(int num)
    {
        try
        {
            f.write(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void seekToPage(int id)
    {
        this.seek(dataOffset + id * pageSize);
    }
    public void seekToPage(int id, Location location, int offset)
    {
        this.seek(dataOffset + id * pageSize + offset + (location == Location.END ? pageSize : 0));
    }
    public Page allocateNewPage(int after)
    {
        if (after >= 0)
        {
            this.seekToPage(after);
            this.writeInt(pagesNum);
        }
        Page p = new Page(pagesNum, -1, this);
        p.clean();
        pagesNum++;
        this.seek(4);
        this.writeInt(pagesNum);
        return p;
    }
    public void seek(long pos)
    {
        try {
            f.seek(pos);
        }
        catch (Exception e)
        {
            return;
        }
    }
    public enum Location{
        START, END;
    }
    public int readByte()
    {

        try {
            return f.read();
        }
        catch (Exception e)
        {
            return 0;
        }
    }
    public long tell()
    {
        try {
            return f.getFilePointer();
        }
        catch (Exception e)
        {
            return 0;
        }
    }
    public void open()
    {
        try {
            f = new RandomAccessFile(getFileName(), "rw");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public Page loadPage(int id)
    {
        Page page = new Page(id, 0, this);
        page.reload();
        return page;
    }
    public void close()
    {
        try {
            f.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public String readDynamicString()
    {
        try {
            int length = this.readInt();
            byte[] bytes = new byte[length];
            f.read(bytes);
            return new String(bytes);
        } catch (Exception e)
        {
            return null;
        }
    }
    public int readInt()
    {
        try {
            byte[] bytes = new byte[4];
            f.read(bytes);
            return ByteBuffer.wrap(bytes).getInt();
        }
        catch (Exception e)
        {
            return 0;
        }
    }
    public Tuple loadTuple(Address a, RelationalAlgebra.Header h)
    {
        Page p = loadPage(a.getPage());
        p.setCurrentSlot(a.getSlot());
        return p.fetch(h);
    }

    public Table getTable(String name)
    {
        //System.out.println(name);
        for (Table t: this.tables)
        {
            if (t.getName().equals(name))
            {
                return t;
            }
        }
        return null;
    }
    public void buildIndexes()
    {
        //System.out.println("loading indexes...");
        for (Table t: this.tables)
        {
            t.buildIndexes();
        }
    }
    public String getFileName()
    {
        return name + ".dat";
    }
}
