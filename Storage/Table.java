package Storage;

import Expressions.Condition;
import Expressions.Expression;
import Index.BTree;
import RelationalAlgebra.*;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by ����� on 20.11.2015.
 */
public class Table extends Set {
    private String name;
    private Header header;
    private int lastAutoincrement = 0;
    private int startPage;
    private int curPage;
    private Database db;
    private long startPagePos = -1;

    public long getStartPagePos() {
        return startPagePos;
    }

    public void setStartPagePos(long startPagePos) {
        this.startPagePos = startPagePos;
    }

    public void setDb(Database db) {
        this.db = db;
    }

    public int getCurPage() {
        return curPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelationalAlgebra.Header getHeader() {
        return header.toRA();
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public int getLastAutoincrement() {
        return lastAutoincrement;
    }

    public void setLastAutoincrement(String lastAutoincrement) {
        this.lastAutoincrement = Integer.parseInt(lastAutoincrement);
    }

    public Table(String name, Header header, int lastAutoincrement, int startPage, int curPage) {
        this.name = name;
        this.header = header;
        this.lastAutoincrement = lastAutoincrement;
        this.startPage = startPage;
        this.curPage = curPage;
    }

    public byte[] getMetaData() {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            buf.write(ByteBuffer.allocate(4).putInt(name.length()).array());
            buf.write(name.getBytes());
            buf.write(ByteBuffer.allocate(4).putInt(startPage).array());
            buf.write(ByteBuffer.allocate(4).putInt(curPage).array());
            buf.write(ByteBuffer.allocate(4).putInt(lastAutoincrement).array());
            buf.write(header.getMetaData());
            return buf.toByteArray();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public void rewriteStatistics()
    {
        db.seek(startPagePos);
        db.writeInt(startPage);
        db.writeInt(curPage);
        db.writeInt(lastAutoincrement);
    }
    public BTree<Primitive, Address> getIndex(String name)
    {
        for (Column c: header.getColumns())
        {
            if (c.getName().equals(name) && c.isIndexed())
            {
                return c.getIndex();
            }
        }
        return null;
    }
    @Override
    public boolean add(Tuple t) {
        if (!header.conform(t))
        {
            return false;
        }
        Page page = null;
        if (this.curPage < 0)
        {
            page = db.allocateNewPage(-1);
            startPage = curPage = page.getId();
        }
        else
        {
            page = db.loadPage(curPage);
        }
        while (!page.insert(t))
        {
            page = db.allocateNewPage(page.getId());
            curPage = page.getId();
        }
        this.rewriteStatistics();
        return true;
    }

    @Override
    public RAMSet limit(int n) {
        int cur = startPage;
        RAMSet set = new RAMSet(header.toRA());
        Tuple t = null;
        int i = 0;
        while (cur != -1 && i < n)
        {
            Page page = db.loadPage(cur);
            while(i < n && (t = page.fetch(header.toRA())) != null)
            {
                set.add(t);
                i++;
            }
            cur = page.getNext();
        }
        return set;
    }
    public Database getDB()
    {
        return db;
    }
    public RAMSet all() {
        int cur = startPage;
        RelationalAlgebra.Header h = header.toRA();
        RAMSet set = new RAMSet(h);
        Tuple t = null;
        while (cur != -1)
        {
            Page page = db.loadPage(cur);
            while((t = page.fetch(h)) != null)
            {
                set.add(t);
            }
            cur = page.getNext();
        }
        return set;
    }

    @Override
    public RAMSet order(Expression[] expressions, Expression.Order[] orders, int limit) {
        int cur = startPage;
        RelationalAlgebra.Header h = header.toRA();
        OrderedRAMSetBuilder set = new OrderedRAMSetBuilder(limit, expressions, orders, h);
        Tuple t = null;
        while (cur != -1)
        {
            Page page = db.loadPage(cur);
            while((t = page.fetch(h)) != null)
            {
                set.add(t);
            }
            cur = page.getNext();
        }
        return set.get();
    }

    @Override
    public RAMSet selection(Condition condition) {
        int cur = startPage;
        RelationalAlgebra.Header h = header.toRA();
        RAMSet set = new RAMSet(h);
        Tuple t = null;
        while (cur != -1)
        {
            Page page = db.loadPage(cur);
            while((t = page.fetch(h)) != null)
            {
                if (condition.match(h, t)) {
                    set.add(t);
                }
            }
            cur = page.getNext();
        }
        return set;
    }
    public void buildIndexes()
    {
        int cur = startPage;
        RelationalAlgebra.Header h = header.toRA();
        RAMSet set = new RAMSet(h);
        Tuple t = null;
        System.out.print("  " + name + "...");
        if (name.equals("Person") || name.equals("Alias"))
        while (cur != -1)
        {
            Page page = db.loadPage(cur);
            while((t = page.fetch(h)) != null)
            {
                for (int i = 0; i < header.getColumns().size(); i++)
                {
                    if (header.getColumns().get(i).isIndexed())
                    {
                        header.getColumns().get(i).pushAddress(t.get(i), new Address(page.getId(), page.getCurrentSlot() - 1));
                    }
                }
                //System.out.println(page.getId() + " " + page.getCurrentSlot());

            }
            cur = page.getNext();
        }
        System.out.println("[OK]");
    }
    public void addIndex(String s)
    {
        for (Column c: header.getColumns())
        {
            if (c.getName().equals(s)) {
                c.setIndexed(true);
            }
        }
    }
    public RAMSet IndexSort(String field, Expression.Order order, int limit) {
        int cur = startPage;
        RelationalAlgebra.Header h = header.toRA();
        RAMSet set = new RAMSet(h);
        Tuple t = null;
        BTree<Primitive, Address> index = getIndex(field);
        if (index == null)
        {
            return set;
        }
        ArrayList<Address> addresses = index.GetMax(limit);
        for (Address a: addresses)
        {
            set.add(db.loadTuple(a, h));
        }
        return set;
    }
    public RAMSet selectIndexed(String field, Primitive p) {
        int cur = startPage;
        RelationalAlgebra.Header h = header.toRA();
        RAMSet set = new RAMSet(h);
        Tuple t = null;
        BTree<Primitive, Address> index = getIndex(field);
        if (index == null)
        {
            return set;
        }
        ArrayList<Address> addresses = index.search(p);
        for (Address a: addresses)
        {
            set.add(db.loadTuple(a, h));
        }
        return set;
    }
}
