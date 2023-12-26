package Domain;

import Domain.Value.Value;

import java.util.Map;

public interface MyIDictionary<T, T1> {

    boolean isDefined(T id);

    T1 lookup(T id);

    void update(T id, T1 val);

    void add(T name, T1 val);

    int size();

    boolean isEmpty();

    Map<T,T1> getContent();

    MyIDictionary<T, T1> deepCopy();
}
