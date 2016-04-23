package Storage;

import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by ����� on 20.11.2015.
 */
public class DatabaseBuilder {
    String name;
    ArrayList<Table> tables;
    RandomAccessFile out;

    public DatabaseBuilder(String name) {
        this.name = name;
        this.tables = new ArrayList<Table>();
    }
    public String getFileName()
    {
        return name + ".dat";
    }
    public void add (Table t)
    {
        this.tables.add(t);
    }
    public boolean writeMetaData()
    {
        try {
            int PagesSize = 4096 * 2;
            int numberOfPages = 0;
            out = new RandomAccessFile(this.getFileName(), "rw");
            out.write(ByteBuffer.allocate(4).putInt(PagesSize).array());
            out.write(ByteBuffer.allocate(4).putInt(numberOfPages).array());
            out.write(ByteBuffer.allocate(4).putInt(tables.size()).array());
            for (Table t: this.tables)
            {
                out.write(t.getMetaData());
            }
            out.close();
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
