package Domain.Exp;

import Domain.MyException;
import Domain.MyIDictionary;
import Domain.MyIHeap;
import Domain.Type.RefType;
import Domain.Type.Type;
import Domain.Value.Value;

public class rH implements Exp {
    private Exp e;

    public rH(Exp e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value val = e.eval(symTbl, heap);
        Value value;
        if (val.getType().equals(new RefType())) {
            int addres = (int) val.getvalue();
            if (heap.isDefined(addres)) {
                value = heap.lookup(addres);
            } else {
                throw new MyException("the address is not defined in the heap");
            }
        } else {
            throw new MyException("the expression doesn't evaluate to a RefType");
        }
        return value;
    }

    @Override
    public Exp deepCopy() {
        return new rH(e);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = e.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        } else {
            throw new MyException("the rH argument is not a RefType");
        }
    }

    @Override
    public String toString() {
        return "RH("+e.toString()+")";
    }
}
