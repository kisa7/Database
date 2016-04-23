import Expressions.ColumnExpression;
import Expressions.Condition;
import Expressions.EmptyCondition;
import Expressions.Expression;
import RelationalAlgebra.Primitive;
import RelationalAlgebra.Primitives.RAInteger;
import RelationalAlgebra.Set;
import Storage.Database;

import java.util.Scanner;

/**
 * Created by Наська on 22.11.2015.
 */
public class DBLP {
    Database db;
    public DBLP(Database db)
    {
        this.db = db;
    }
    public Set CheckUser(Scanner in)
    {
        final String email = in.next();
        if (!in.hasNext())
        {
            return null;
        }
        final String pass = in.next();
        return db.getTable("Users").selection(new Condition() {
            @Override
            public boolean expr() {
                return get("Email").equals(email) && get("Password").equals(pass);
            }
        });
    }
    public Set getPerson(Scanner in) {
        int id = Integer.parseInt(in.next());
        //SELECT Person.Info, Person.Homepage, Person.Rank, Alias.Alias, Person.Photo FROM Person JOIN Alias ON Alias.PersonID = Person.ID WHERE Person.ID = 22
        return db.getTable("Person").selectIndexed("ID", new RAInteger(id)).join(db.getTable("Alias"), new ColumnExpression("ID"), "PersonID", new EmptyCondition());
    }
}
