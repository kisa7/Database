package RelationalAlgebra.Primitives;

import RelationalAlgebra.Primitive;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

/**
 * Created by ����� on 20.11.2015.
 */
public class RAInteger extends Primitive {
    private int value;
    public RAInteger(int value)
    {
        this.value = value;
    }
    @Override
    public String getDisplayedValue() {
        return "" + value;
    }

    @Override
    public Type getType() {
        return Type.INT;
    }

    @Override
    public boolean isTrue() {
        return value != 0;
    }
    public int getValue()
    {
        return value;
    }
    @Override
    public boolean greater(Primitive other) {
        return this.value > ((RAInteger)other).getValue();
    }
    @Override
    public boolean equals(int s) {return value == s;}
    @Override
    public Primitive plus(Primitive other) {
        return new RAInteger(this.value + ((RAInteger)other).getValue());
    }

    @Override
    public byte[] getDump() {
        return ByteBuffer.allocate(4).putInt(value).array();
    }
    public static RAInteger fromByteStream(ByteArrayInputStream stream)
    {
        try {
            byte[] bytes = new byte[4];
            stream.read(bytes);
            return new RAInteger(ByteBuffer.wrap(bytes).getInt());
        }
        catch (Exception e)
        {
            return new RAInteger(0);
        }
    }
}
