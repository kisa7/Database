import RelationalAlgebra.RAMSet;
import RelationalAlgebra.Set;
import Storage.Database;
import com.sun.corba.se.spi.activation.Server;
import sun.misc.IOUtils;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by yar 09.09.2009
 */
public class MyMySQLServer {
    private static void writeResponse(Socket ss, String s) throws Throwable {
        ss.getOutputStream().write(s.getBytes());
        ss.getOutputStream().flush();
    }
    DBLP dblp;
    ServerSocket ss;
    public void run()
    {
        while (true) {
            try {
                Socket s = ss.accept();
                System.out.println("Client accepted");
                try {
                    Scanner sc = new Scanner(s.getInputStream());
                    String function = sc.next();
                    Set set = null;
                    if (function.equals("CheckUser"))
                    {
                        set = dblp.CheckUser(sc);
                    }
                    if (function.equals("getPerson"))
                    {
                        set = dblp.getPerson(sc);
                    }
                    if (set != null)
                        writeResponse(s, set.getJson());
                    else
                        writeResponse(s, "{}");
                } catch (Throwable t) {
                    t.printStackTrace();
                } finally {
                    try {
                        s.close();
                    } catch (Throwable t) {
                    /*do nothing*/
                    }
                }
                //System.out.println("Client processing finished");
            } catch (Throwable t)
            {
                System.out.println("hello");
            }
        }
    }
    public MyMySQLServer(Database db){
        this.dblp = new DBLP(db);
        try {
         this.ss = new ServerSocket(2020
         );
        }
        catch (Exception e)
        {
            System.out.println("can't run a server");
            return;
        }
    }
}