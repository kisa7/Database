import Expressions.ColumnExpression;
import Expressions.EmptyCondition;
import Expressions.Expression;
import RelationalAlgebra.*;
import RelationalAlgebra.Primitives.*;
import Storage.*;
import Storage.Column;
import Storage.Header;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //clear();
        Database d = new Database("mydblp");
        d.getTable("Person").addIndex("Rank");

         d.buildIndexes();
        /*d.getTable("Alias").add(new Tuple(new Primitive[]{new RAInteger(5), new RAString("Sergey Solonets"), new RAInteger(3)}));
        d.getTable("Alias").add(new Tuple(new Primitive[]{new RAInteger(1), new RAString("Kisa7"), new RAInteger(3)}));
        d.getTable("Alias").add(new Tuple(new Primitive[]{new RAInteger(5), new RAString("Moon"), new RAInteger(2)}));
        d.getTable("Alias").add(new Tuple(new Primitive[]{new RAInteger(2), new RAString("Moon"), new RAInteger(3)}));
        d.getTable("Alias").add(new Tuple(new Primitive[]{new RAInteger(6), new RAString("Kisa72"), new RAInteger(3)}));
        d.getTable("Alias").add(new Tuple(new Primitive[]{new RAInteger(7), new RAString("Moons"), new RAInteger(2)}));
        d.getTable("Alias").add(new Tuple(new Primitive[]{new RAInteger(3), new RAString("Moon"), new RAInteger(3)}));*/
        //Set s = d.getTable("Person").IndexSort("Rank", Expression.Order.ASC, 5);
        //System.out.println(s);
        //1Set a = s.join(d.getTable("Alias"), new ColumnExpression("ID"), "PersonID", new EmptyCondition());
        //System.out.println(a.getJson());

        MyMySQLServer server = new MyMySQLServer(d);
        server.run();
        d.close();
    }
    public static void clear()
    {
        DatabaseBuilder db = new DatabaseBuilder("mydblp");
        db.add(new Table("Alias", new Header(new Column[]{
                new Column("ID", Primitive.Type.INT, true, true),
                new Column("Alias", Primitive.Type.STRING, false, false),
                new Column("PersonID", Primitive.Type.INT, false, true)
        }), 0, -1, -1));
        db.add(new Table("Article", new Header(new Column[]{
                new Column("PublicationID", Primitive.Type.INT, false, true),
                new Column("EditionID", Primitive.Type.INT, false, true)
        }), 0, -1, -1));
        db.add(new Table("AuthorEditor", new Header(new Column[]{
                new Column("AliasID", Primitive.Type.INT, false, true),
                new Column("PublicationID", Primitive.Type.INT, false, true),
                new Column("Type", Primitive.Type.STRING, false, false)
        }), 0, -1, -1));
        db.add(new Table("Book", new Header(new Column[]{
                new Column("PublicationID", Primitive.Type.INT, false, true),
                new Column("BookTitle", Primitive.Type.STRING, false, false),
                new Column("ISBN", Primitive.Type.STRING, false, false),
                new Column("Volume", Primitive.Type.STRING, false, false),
                new Column("Year", Primitive.Type.INT, false, false),
                new Column("MonthStart", Primitive.Type.INT, false, false),
                new Column("MonthEnd", Primitive.Type.INT, false, false),
                new Column("Adress", Primitive.Type.STRING, false, false),
                new Column("PublisherID", Primitive.Type.INT, false, true),
                new Column("SerieID", Primitive.Type.INT, false, false),
                new Column("SchoolID", Primitive.Type.INT, false, false)
        }), 0, -1, -1));
        db.add(new Table("Cite", new Header(new Column[]{
                new Column("PublicationID1", Primitive.Type.INT, false, true),
                new Column("PublicationID2", Primitive.Type.INT, false, true)
        }), 0, -1, -1));
        db.add(new Table("Edition", new Header(new Column[]{
                new Column("ID", Primitive.Type.INT, true, true),
                new Column("Year", Primitive.Type.INT, false, false),
                new Column("MonthStart", Primitive.Type.INT, false, false),
                new Column("MonthEnd", Primitive.Type.INT, false, false),
                new Column("Volume", Primitive.Type.STRING, false, false),
                new Column("Issue", Primitive.Type.STRING, false, true),
                new Column("JournalID", Primitive.Type.INT, false, true)
        }), 0, -1, -1));
        db.add(new Table("Incollection", new Header(new Column[]{
                new Column("PublicationID", Primitive.Type.INT, false, true),
                new Column("BookID", Primitive.Type.INT, false, true)
        }), 0, -1, -1));
        db.add(new Table("Inproceeding", new Header(new Column[]{
                new Column("PublicationID", Primitive.Type.INT, false, true),
                new Column("ProceedingID", Primitive.Type.INT, false, true)
        }), 0, -1, -1));
        db.add(new Table("Journal", new Header(new Column[]{
                new Column("ID", Primitive.Type.INT, false, true),
                new Column("Name", Primitive.Type.STRING, false, false)
        }), 0, -1, -1));
        db.add(new Table("Person", new Header(new Column[]{
                new Column("ID", Primitive.Type.INT, false, true),
                new Column("DBLPKey", Primitive.Type.STRING, false, false),
                new Column("Info", Primitive.Type.STRING, false, false),
                new Column("ModificationDate", Primitive.Type.STRING, false, false),
                new Column("Homepage", Primitive.Type.STRING, false, false),
                new Column("Photo", Primitive.Type.STRING, false, false),
                new Column("Rank", Primitive.Type.REAL, false, false)
        }), 0, -1, -1));
        db.add(new Table("Proceeding", new Header(new Column[]{
                new Column("PublicationID", Primitive.Type.INT, false, true),
                new Column("BookTitle", Primitive.Type.STRING, false, false),
                new Column("ISBN", Primitive.Type.STRING, false, false),
                new Column("Volume", Primitive.Type.STRING, false, false),
                new Column("Year", Primitive.Type.INT, false, false),
                new Column("MonthStart", Primitive.Type.INT, false, false),
                new Column("MonthEnd", Primitive.Type.INT, false, false),
                new Column("Adress", Primitive.Type.STRING, false, false),
                new Column("PublisherID", Primitive.Type.INT, false, true),
                new Column("SerieID", Primitive.Type.INT, false, true),
                new Column("Issue", Primitive.Type.STRING, false, false)
        }), 0, -1, -1));
        db.add(new Table("Publication", new Header(new Column[]{
                new Column("ID", Primitive.Type.INT, true, true),
                new Column("Title", Primitive.Type.STRING, false, false),
                new Column("DBLPKey", Primitive.Type.STRING, false, false),
                new Column("Pages", Primitive.Type.STRING, false, false),
                new Column("ModificationDate", Primitive.Type.STRING, false, false),
                new Column("Note", Primitive.Type.STRING, false, false),
                new Column("Rank", Primitive.Type.REAL, false, false)
        }), 0, -1, -1));
        db.add(new Table("Publisher", new Header(new Column[]{
                new Column("ID", Primitive.Type.INT, true, true),
                new Column("Name", Primitive.Type.STRING, false, false)
        }), 0, -1, -1));
        db.add(new Table("School", new Header(new Column[]{
                new Column("ID", Primitive.Type.INT, true, true),
                new Column("Name", Primitive.Type.STRING, false, false)
        }), 0, -1, -1));
        db.add(new Table("Serie", new Header(new Column[]{
                new Column("ID", Primitive.Type.INT, true, true),
                new Column("Name", Primitive.Type.STRING, false, false),
                new Column("DBLPKey", Primitive.Type.STRING, false, false)
        }), 0, -1, -1));
        db.add(new Table("Thesis", new Header(new Column[]{
                new Column("PublicationID", Primitive.Type.INT, false, true),
                new Column("Type", Primitive.Type.STRING, false, false),
                new Column("Year", Primitive.Type.INT, false, false),
                new Column("MonthStart", Primitive.Type.INT, false, false),
                new Column("MonthEnd", Primitive.Type.INT, false, false),
                new Column("SchoolID", Primitive.Type.INT, false, true)
        }), 0, -1, -1));
        db.add(new Table("URL", new Header(new Column[]{
                new Column("ID", Primitive.Type.INT, true, true),
                new Column("URL", Primitive.Type.STRING, false, false),
                new Column("PublicationID", Primitive.Type.INT, false, false)
        }), 0, -1, -1));
        db.add(new Table("Users", new Header(new Column[]{
                new Column("ID", Primitive.Type.INT, true, true),
                new Column("Email", Primitive.Type.STRING, false, false),
                new Column("Name", Primitive.Type.STRING, false, false),
                new Column("LastName", Primitive.Type.STRING, false, false),
                new Column("Password", Primitive.Type.STRING, false, false),
                new Column("Privelege", Primitive.Type.STRING, false, false),
                new Column("Photo", Primitive.Type.STRING, false, false)
        }), 0, -1, -1));
        db.writeMetaData();
    }
    public static void loadAlias(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Alias.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            t.add(new RAInteger(s[2].equals("NULL") ? 0 : Integer.parseInt(s[2])));
            table.add(t);
        }
        in.close();
    }

    public static void loadArticle(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Article.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAInteger(s[1].equals("NULL") ? 0 : Integer.parseInt(s[1])));
            table.add(t);
        }
    }

    public static void loadAuthorEditor(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("AuthorEditor.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAInteger(s[1].equals("NULL") ? 0 : Integer.parseInt(s[1])));
            t.add(new RAString(s[2].equals("NULL") ? "" : s[2]));
            table.add(t);
        }
        in.close();
    }

    public static void loadBook(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Book.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            t.add(new RAString(s[2].equals("NULL") ? "" : s[2]));
            t.add(new RAString(s[3].equals("NULL") ? "" : s[3]));
            t.add(new RAInteger(s[4].equals("NULL") ? 0 : Integer.parseInt(s[4])));
            t.add(new RAInteger(s[5].equals("NULL") ? 0 : Integer.parseInt(s[5])));
            t.add(new RAInteger(s[6].equals("NULL") ? 0 : Integer.parseInt(s[6])));
            t.add(new RAString(s[7].equals("NULL") ? "" : s[7]));
            t.add(new RAInteger(s[8].equals("NULL") ? 0 : Integer.parseInt(s[8])));
            t.add(new RAInteger(s[9].equals("NULL") ? 0 : Integer.parseInt(s[9])));
            t.add(new RAInteger(s[10].equals("NULL") ? 0 : Integer.parseInt(s[10])));
            table.add(t);
        }
        in.close();
    }

    public static void loadCite(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Cite.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAInteger(s[1].equals("NULL") ? 0 : Integer.parseInt(s[1])));
            table.add(t);
        }
    }

    public static void loadEdition(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Edition.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAInteger(s[1].equals("NULL") ? 0 : Integer.parseInt(s[1])));
            t.add(new RAInteger(s[2].equals("NULL") ? 0 : Integer.parseInt(s[2])));
            t.add(new RAInteger(s[3].equals("NULL") ? 0 : Integer.parseInt(s[3])));
            t.add(new RAString(s[4].equals("NULL") ? "" : s[4]));
            t.add(new RAString(s[5].equals("NULL") ? "" : s[5]));
            t.add(new RAInteger(s[6].equals("NULL") ? 0 : Integer.parseInt(s[6])));
            table.add(t);
        }
        in.close();
    }

    public static void loadIncollection(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Incollection.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAInteger(s[1].equals("NULL") ? 0 : Integer.parseInt(s[1])));
            table.add(t);
        }
    }

    public static void loadInproceeding(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Inproceeding.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAInteger(s[1].equals("NULL") ? 0 : Integer.parseInt(s[1])));
            table.add(t);
        }
    }

    public static void loadJournal(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Journal.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            table.add(t);
        }
    }

    public static void loadPerson(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Person.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String[] s = null;
            String v = "";
            while (s == null || s.length < 7) {
                v = v + in.nextLine();
                s = v.split("\\|\\|\\|");
            }
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            t.add(new RAString(s[2].equals("NULL") ? "" : s[2]));
            t.add(new RAString(s[3].equals("NULL") ? "" : s[3]));
            t.add(new RAString(s[4].equals("NULL") ? "" : s[4]));
            t.add(new RAString(s[5].equals("NULL") ? "" : s[5]));
            t.add(new RAReal(s[6].equals("NULL") ? 0 : Double.parseDouble(s[6])));
            table.add(t);
        }
        in.close();
    }

    public static void loadProceeding(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Proceeding.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            t.add(new RAString(s[2].equals("NULL") ? "" : s[2]));
            t.add(new RAString(s[3].equals("NULL") ? "" : s[3]));
            t.add(new RAInteger(s[4].equals("NULL") ? 0 : Integer.parseInt(s[4])));
            t.add(new RAInteger(s[5].equals("NULL") ? 0 : Integer.parseInt(s[5])));
            t.add(new RAInteger(s[6].equals("NULL") ? 0 : Integer.parseInt(s[6])));
            t.add(new RAString(s[7].equals("NULL") ? "" : s[7]));
            t.add(new RAInteger(s[8].equals("NULL") ? 0 : Integer.parseInt(s[8])));
            t.add(new RAInteger(s[9].equals("NULL") ? 0 : Integer.parseInt(s[9])));
            t.add(new RAString(s[10].equals("NULL") ? "" : s[10]));
            table.add(t);
        }
        in.close();
    }

    public static void loadPublication(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Publication.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            t.add(new RAString(s[2].equals("NULL") ? "" : s[2]));
            t.add(new RAString(s[3].equals("NULL") ? "" : s[3]));
            t.add(new RAString(s[4].equals("NULL") ? "" : s[4]));
            t.add(new RAString(s[5].equals("NULL") ? "" : s[5]));
            t.add(new RAReal(s[6].equals("NULL") ? 0 : Double.parseDouble(s[6])));
            table.add(t);
        }
        in.close();
    }

    public static void loadPublisher(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Publisher.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            table.add(t);
        }
    }

    public static void loadSchool(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("School.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            table.add(t);
        }
    }

    public static void loadSerie(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Serie.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            t.add(new RAString(s[2].equals("NULL") ? "" : s[2]));
            table.add(t);
        }
    }

    public static void loadThesis(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Thesis.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            t.add(new RAInteger(s[2].equals("NULL") ? 0 : Integer.parseInt(s[2])));
            t.add(new RAInteger(s[3].equals("NULL") ? 0 : Integer.parseInt(s[3])));
            t.add(new RAInteger(s[4].equals("NULL") ? 0 : Integer.parseInt(s[4])));
            t.add(new RAInteger(s[5].equals("NULL") ? 0 : Integer.parseInt(s[5])));
            table.add(t);
        }
        in.close();
    }
    public static void loadURL(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("URL.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            t.add(new RAInteger(s[2].equals("NULL") ? 0 : Integer.parseInt(s[2])));
            table.add(t);
        }
    }

    public static void loadUsers(Table table){
        Scanner in;
        Primitive p;
        try {
            in = new Scanner(new File("Users.csv"));
        } catch (Exception e) {
            return;
        }
        while(in.hasNext()) {
            String v = in.nextLine();
            String[] s = v.split("\\|\\|\\|");
            Tuple t = new Tuple();
            t.add(new RAInteger(s[0].equals("NULL") ? 0 : Integer.parseInt(s[0])));
            t.add(new RAString(s[1].equals("NULL") ? "" : s[1]));
            t.add(new RAString(s[2].equals("NULL") ? "" : s[2]));
            t.add(new RAString(s[3].equals("NULL") ? "" : s[3]));
            t.add(new RAString(s[4].equals("NULL") ? "" : s[4]));
            t.add(new RAString(s[5].equals("NULL") ? "" : s[5]));
            t.add(new RAString(s[6].equals("NULL") ? "" : s[6]));
            table.add(t);
        }
        in.close();
    }
}

