package Domain;

import Domain.Value.Value;

import java.util.List;

public class MyList <T> implements MyIList<T>
{
    public MyList(){
        list=new java.util.ArrayList<T>();
    }
    private java.util.List<T> list;
    @Override
    public void add(T eval) {
        list.add(eval);
    }
    @Override
    public boolean contains(T eval){
        return list.contains(eval);
    }
    @Override
    public void remove(T eval){
        list.remove(eval);
    }

    @Override
    public List<T> getContent() {
        return list;
    }

    @Override
    public String toString(){
        String s="";
        for(T e:list){
            s+=e.toString()+"\n";
        }
        return s;
    }
}
