package Domain.Exp;

import Domain.MyException;
import Domain.MyIDictionary;
import Domain.MyIHeap;
import Domain.Type.Type;
import Domain.Value.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer,Value> heap) throws MyException;

    Exp deepCopy();

    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
