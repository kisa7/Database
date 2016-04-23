package RelationalAlgebra.Primitives;

import RelationalAlgebra.Primitive;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.ws.server.provider.SyncProviderInvokerTube;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Created by ����� on 20.11.2015.
 */
public class RAString extends Primitive {
    private String value;
    public RAString(String value)
    {
        this.value = value;
    }
    @Override
    public String getDisplayedValue() {
        return "\"" + value + "\"";
    }
    @Override
    public Type getType() {
        return Type.STRING;
    }

    @Override
    public boolean isTrue() {
        return !value.equals("");
    }
    public String getValue()
    {
        return value;
    }
    @Override
    public boolean greater(Primitive other) {
        return value.compareTo(((RAString)other).getValue()) > 0;
    }

    @Override
    public Primitive plus(Primitive other) {
        return new RAString(value + ((RAString)other).getValue());
    }
    @Override
    public boolean equals(String s) {return value.equals(s);}
    @Override
    public byte[] getDump() {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            buf.write(ByteBuffer.allocate(4).putInt(value.length()).array());
            buf.write(value.getBytes());
            return buf.toByteArray();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public static RAString fromByteStream(ByteArrayInputStream stream)
    {
        try {
            byte[] bytes = new byte[4];
            stream.read(bytes);
            int length = ByteBuffer.wrap(bytes).getInt();
            if (length > 1000)
            {
                return new RAString("");
            }
            byte[] str = new byte[length];
            stream.read(str);
            String s = new String(str, StandardCharsets.UTF_8);
            return new RAString(s);
        } catch (Exception e)
        {
            return new RAString("");
        }
    }

}
