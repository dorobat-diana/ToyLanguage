package Domain;

import Domain.Value.Value;

import java.util.List;

public interface MyIList <T>{
    void add(T eval);

    boolean contains(T eval);

    void remove(T eval);

    List<T> getContent();
}
