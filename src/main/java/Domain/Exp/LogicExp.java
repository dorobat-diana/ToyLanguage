package Domain.Exp;

import Domain.*;
import Domain.Type.BoolType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.Value;

public class LogicExp implements Exp {
    Exp e1;
    Exp e2;
    int op; // 1 - and, 2 - or
    LogicExp(Exp e1,Exp e2,int op){
        this.e1=e1;
        this.e2=e2;
        this.op=op;
    }
    public Value eval(MyIDictionary<String,Value> tbl,MyIHeap<Integer,Value>heap) throws MyException {
        Value v1,v2;
        v1= e1.eval(tbl,heap);
        if(v1.getType().equals(new BoolType())){
            v2=e2.eval(tbl,heap);
            if(v2.getType().equals(new BoolType())){
                BoolValue i1=(BoolValue)v1;
                BoolValue i2=(BoolValue)v2;
                boolean n1,n2;
                n1= (boolean) i1.getvalue();
                n2= (boolean) i2.getvalue();
                if(op==1) return new BoolValue(n1 && n2);
                if(op==2) return new BoolValue(n1 || n2);
            }
            else throw new MyException("second operand is not a boolean");
        }
        else throw new MyException("first operand is not a boolean");
        return new BoolValue(false);
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(e1.deepCopy(),e2.deepCopy(),op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1,type2;
        type1=e1.typecheck(typeEnv);
        type2=e2.typecheck(typeEnv);
        if(type1.equals(new BoolType())){
            if(type2.equals(new BoolType())){
                return new BoolType();
            }
            else throw new MyException("second operand is not a boolean");
        }
        else throw new MyException("first operand is not a boolean");
    }
}
