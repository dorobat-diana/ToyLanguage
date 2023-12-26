package Domain.Exp;

import Domain.MyException;
import Domain.MyIDictionary;
import Domain.MyIHeap;
import Domain.Type.Type;
import Domain.Value.Value;

public class VarExp implements Exp {
    String id;
    public VarExp(String id){
        this.id = id;
    }
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer,Value>heap) throws MyException {
        return symTbl.lookup(id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    public String toString(){
        return id;
    }
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        return (Type)typeEnv.lookup(id);
    }
}
