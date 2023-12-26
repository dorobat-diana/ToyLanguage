package Domain.Exp;

import Domain.MyException;
import Domain.MyIDictionary;
import Domain.MyIHeap;
import Domain.Type.Type;
import Domain.Value.Value;

public class ValueExp implements Exp {
    Value e;
    public ValueExp(Value e){
        this.e = e;
    }
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer,Value>heap) throws MyException {
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e.deepCopy());
    }

    public String toString(){
        return e.toString();
    }
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        return e.getType();
    }
}
