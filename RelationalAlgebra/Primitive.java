package RelationalAlgebra;

/**
 * Created by ����� on 20.11.2015.
 */
public abstract class Primitive implements Comparable<Primitive>{
    public static enum Type{
        INT(0), REAL(1), STRING(2);
        int id;
        Type(int id) {this.id = id;}
        public static Type fromId(int id)
        {
            switch (id)
            {
                case 0:
                    return INT;
                case 1:
                    return REAL;
                case 2:
                    return STRING;
                default:
                    return INT;
            }
        }
        public int getId(){ return id; };
    }
    public abstract String getDisplayedValue();
    public abstract Type getType();
    public abstract boolean isTrue();
    public abstract boolean greater(Primitive other);
    public boolean less(Primitive other) {return other.greater(this);}
    public boolean equals(Primitive other) {return !other.greater(this) && !greater(other);}
    public boolean greaterOrEquals(Primitive other) {return !other.less(this);}
    public boolean lessOrEquals(Primitive other) {return !other.greater(this);}
    public abstract Primitive plus(Primitive other);
    public abstract byte[] getDump();
    @Override
    public int compareTo(Primitive o) {
        return greater(o) ? 1 : (less(o) ? -1 : 0);
    }
    public boolean equals(String s) {return false;}
    public boolean equals(int s) {return false;}
    public boolean equals(double s) {return false;}
}
