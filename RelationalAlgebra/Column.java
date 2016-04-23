package RelationalAlgebra;

/**
 * Created by ����� on 20.11.2015.
 */
public class Column {
    private String name;
    private Primitive.Type type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        if(type.equals(Primitive.Type.INT)) {
            this.type = Primitive.Type.INT;
        }
        else if(type.equals(Primitive.Type.REAL)) {
            this.type = Primitive.Type.REAL;
        }
        else {
            this.type = Primitive.Type.STRING;
        }
    }

    public Column(String name, Primitive.Type type) {
        this.name = name;
        this.type = type;
    }
    public Primitive.Type getType()
    {
        return type;
    }
}
