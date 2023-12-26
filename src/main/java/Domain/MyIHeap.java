package Domain;
import Domain.Exp.Exp;
import Domain.Value.Value;

import java.util.HashMap;
import java.util.Map;

public interface MyIHeap <T,T1>{
    void add(T1 val);
    Value lookup(T name);
    void update(T name,T1 val);
    boolean isDefined(T name);
    int get_possition();

    Map<T, T1> getContent();

    void update2(Map<T, T1> integerValueMap);
}
