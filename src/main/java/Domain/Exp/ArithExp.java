package Domain.Exp;

import Domain.*;
import Domain.Type.IntType;
import Domain.Type.Type;
import Domain.Value.IntValue;
import Domain.Value.Value;

public class ArithExp implements Exp {
    private Exp e1;
    private Exp e2;
    private char op;

    public ArithExp(char op,Exp e1, Exp e2){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public Value eval(MyIDictionary<String, Value> symTbl,MyIHeap<Integer,Value>heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(symTbl,heap);
        if(v1.getType().equals(new IntType())){
            v2 = e2.eval(symTbl,heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1, n2;
                n1 = (int) i1.getvalue();
                n2 = (int) i2.getvalue();
                if(op == '+') return new IntValue(n1 + n2);
                if(op == '-') return new IntValue(n1 - n2);
                if(op == '*') return new IntValue(n1 * n2);
                if(op == '/')
                    if(n2 == 0) throw new MyException("division by zero");
                    else return new IntValue(n1 / n2);
            }
            else throw new MyException("second operand is not an integer");
        }
        else throw new MyException("first operand is not an integer");
        return new IntValue(0);
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(op,e1.deepCopy(), e2.deepCopy());
    }

    public String toString(){
        String s = "";
        if(op == 1) s = e1.toString() + " + " + e2.toString();
        if(op == 2) s = e1.toString() + " - " + e2.toString();
        if(op == 3) s = e1.toString() + " * " + e2.toString();
        if(op == 4) s = e1.toString() + " / " + e2.toString();
        return s;
    }
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type type1, type2;
        type1=e1.typecheck(typeEnv);
        type2=e2.typecheck(typeEnv);
        if(type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                return new IntType();
            }
            else throw new MyException("second operand is not an integer");
        }
        else throw new MyException("first operand is not an integer");
    }
}
