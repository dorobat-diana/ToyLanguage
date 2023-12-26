package Domain;

import Domain.Exp.Exp;
import Domain.Value.Value;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyHeap<K, V> implements MyIHeap<K, V> {
    int freeaddres;
    private Map<K, V> heap;

    public MyHeap() {
        heap = new HashMap<>();
        freeaddres = 1;
    }
    @Override
    public String toString(){
        String s="Heap:\n";
        Set<K> k =heap.keySet();
        Iterator<K> i=k.iterator();
        while(i.hasNext()){
            K key= (K) i.next();
            s+= key.toString()+"->"+heap.get(key).toString()+"\n";
        }
        return s;
    }

    @Override
    public void add( V val) {
        heap.put((K) Integer.valueOf(freeaddres), val);
        freeaddres+=1;

    }

    @Override
    public Value lookup(K name) {
        return (Value) heap.get(name);
    }

    @Override
    public void update(K name, V val) {
        heap.replace(name,heap.get(name),val);
    }

    @Override
    public boolean isDefined(K name) {
        return heap.get(name)!=null;
    }

    @Override
    public int get_possition() {
        return freeaddres-1;
    }

    @Override
    public Map<K, V> getContent() {
        return heap;
    }

    @Override
    public void update2(Map<K, V> integerValueMap) {
        heap=integerValueMap;
    }

}
