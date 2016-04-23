package Storage;

import RelationalAlgebra.*;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.util.ArrayList;

/**
 * Created by Булат on 21.11.2015.
 */
public class Page {
    int id;
    int next;
    int records;
    int currentSlot;
    long freeSpace;
    ArrayList<Long> offsets;
    Database db;

    public Page(int id, int next, Database db) {
        this.id = id;
        this.db = db;
        this.next = next;
        this.records = 0;
        this.freeSpace = 4;
        this.currentSlot = 0;
        this.offsets = new ArrayList<>();
    }
    public void clean()
    {
        db.seekToPage(id);
        db.writeInt(next);
        this.currentSlot = 0;
        for (int i = 0; i < db.getPageSize() - 12; i++)
        {
            db.writeByte(0);
        }
        db.writeInt(4);
        db.writeInt(0);
    }
    public void reload()
    {
        db.seekToPage(id);
        next = db.readInt();
        db.seekToPage(id, Database.Location.END, -8);
        freeSpace = db.readInt();
        records = db.readInt();
        db.seekToPage(id, Database.Location.END, -4 * (records + 2));
        this.offsets = new ArrayList<>();
        for (int i = 0; i < records; i++)
        {
            db.seekToPage(id, Database.Location.END, -4 * (3 + i));
            this.offsets.add((long)db.readInt());
        }
    }
    public boolean insert(Tuple t)
    {
        byte[] bytes = t.getDump();
        if (freeSpace + bytes.length + (records + 5) * 4 > db.getPageSize())
        {
            return false;
        }
        records++;
        this.offsets.add(freeSpace);
        db.seekToPage(id, Database.Location.END, -4 * (records + 2));
        db.writeInt((int)freeSpace);
        db.seekToPage(id, Database.Location.START, (int)freeSpace);
        db.writeInt(bytes.length);
        db.writeBlob(bytes);
        freeSpace += bytes.length + 4;
        db.seekToPage(id, Database.Location.END, -8);
        db.writeInt((int)freeSpace);
        db.writeInt(records);
        return true;
    }
    public Tuple fetch(RelationalAlgebra.Header h)
    {
        if (currentSlot >= this.offsets.size())
        {
            return null;
        }
        db.seekToPage(id, Database.Location.START, this.offsets.get(currentSlot).intValue());
        //System.out.println(db.tell());
        byte[] blob = db.readDynamicBlob();
        Tuple t = Tuple.fromByteStream(new ByteInputStream(blob, blob.length),h);
        currentSlot++;
        return t;
    }

    public void setCurrentSlot(int currentSlot) {
        this.currentSlot = currentSlot;
    }

    public int getCurrentSlot() {
        return currentSlot;
    }

    public void restore()
    {
        currentSlot = 0;
    }
    public int getId() {
        return id;
    }

    public int getNext() {
        return next;
    }
}
