package xml;

import java.util.Collection;
import java.util.Map;

public interface XMLParse<T, V> {

    Map<V, T> get();
    void set(Collection<T> t);

}
