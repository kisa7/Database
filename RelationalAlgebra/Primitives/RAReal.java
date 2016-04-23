package RelationalAlgebra.Primitives;

import RelationalAlgebra.Primitive;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

/**
 * Created by ����� on 20.11.2015.
 */
public class RAReal extends Primitive {
    private double value;
    public RAReal(double value)
    {
        this.value = value;
    }
    @Override
    public String getDisplayedValue() {
        return "" + value;
    }

    @Override
    public Type getType() {
        return Type.REAL;
    }

    @Override
    public boolean isTrue() {
        return value != 0;
    }
    public double getValue()
    {
        return value;
    }
    @Override
    public boolean greater(Primitive other) {
        return value > ((RAReal) other).getValue();
    }
    @Override
    public boolean equals(double s) {return value == s;}
    @Override
    public Primitive plus(Primitive other) {
        return new RAReal(value + ((RAReal) other).getValue());
    }

    @Override
    public byte[] getDump() {
        return ByteBuffer.allocate(8).putDouble(value).array();
    }
    public static RAReal fromByteStream(ByteArrayInputStream stream)
    {
        try {
            byte[] bytes = new byte[8];
            stream.read(bytes);
            return new RAReal(ByteBuffer.wrap(bytes).getDouble());
        }
        catch (Exception e)
        {
            return new RAReal(0);
        }
    }
}