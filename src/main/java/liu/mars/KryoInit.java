package liu.mars;

import clojure.lang.*;
import com.esotericsoftware.kryo.Kryo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class KryoInit {
    public void customize(Kryo kryo){
        kryo.register(BigDecimal.class);
        kryo.register(ArrayList.class);
        kryo.register(String.class);
    }
}
