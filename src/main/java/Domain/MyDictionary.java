package Domain;

import Domain.Value.Value;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

public class MyDictionary<T,T1> implements MyIDictionary<T,T1>{
    private Map<T,T1> dict;
    public MyDictionary(){
        dict=new java.util.Hashtable<T,T1>();
    }
    @Override
    public int size() {
        return dict.size();
    }
    @Override
    public boolean isEmpty() {
        if (dict.isEmpty())
            return true;
        return false;
    }

    @Override
    public Map<T,T1> getContent() {
        return  dict;
    }

    @Override
    public MyIDictionary<T, T1> deepCopy() {
        MyIDictionary<T,T1> newdict=new MyDictionary<T,T1>();
        for(T key:dict.keySet()){
            newdict.add(key,dict.get(key));
        }
        return newdict;
    }

    @Override
    public boolean isDefined(T id) {
        return dict.get(id) != null;
    }
    @Override
    public T1 lookup(T id) {
        return dict.get(id);
    }
    @Override
    public void update(T id, T1 val) {
        dict.put(id,val);
    }
    @Override
    public void add(T name, T1 val) {
        dict.put(name,val);
    }
    @Override
    public String toString(){
        String s="";
        Hashtable<T,T1> Dict=new Hashtable<T,T1>(this.dict);
        Enumeration<T> e = Dict.keys();
        while (e.hasMoreElements()) {
            T key = e.nextElement();
            s+=key.toString()+"->"+Dict.get(key).toString()+"\n";
        }
        return s;
    }
}