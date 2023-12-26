package Domain.Exp;

import Domain.MyException;
import Domain.MyIDictionary;
import Domain.MyIHeap;
import Domain.Type.IntType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.Value;

public class RationalExp implements Exp{
    private Exp e1;
    private Exp e2;
    private String op;// "<", "<=", "==", "!=", ">", ">="
    public RationalExp(Exp e1, Exp e2, String op){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer,Value>heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(symTbl,heap);
        v2 = e2.eval(symTbl,heap);
        if (v1.getType().equals(new IntType()) && v2.getType().equals(new IntType())){
            IntValue i1 = (IntValue)v1;
            IntValue i2 = (IntValue)v2;
            int n1, n2;
            n1 = (int) i1.getvalue();
            n2 = (int) i2.getvalue();
            if(op == "<") return new BoolValue(n1 < n2);
            if(op == "<=") return new BoolValue(n1 <= n2);
            if(op == "==") return new BoolValue(n1 == n2);
            if(op == "!=") return new BoolValue(n1 != n2);
            if(op == ">") return new BoolValue(n1 > n2);
            if(op == ">=") return new BoolValue(n1 >= n2);

        }else throw new MyException("first or second operand is not an integer");
        return new BoolValue(false);
    }

    @Override
    public Exp deepCopy() {
        return new RationalExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1=e1.typecheck(typeEnv);
        type2=e2.typecheck(typeEnv);
        if(type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                return new BoolValue(true).getType();
            }
            else throw new MyException("second operand is not an integer");
        }
        else throw new MyException("first operand is not an integer");
    }

    public String toString(){
        String s = "";
        if(op == "<") s = e1.toString() + " < " + e2.toString();
        if(op == "<=") s = e1.toString() + " <= " + e2.toString();
        if(op == "==") s = e1.toString() + " == " + e2.toString();
        if(op == "!=") s = e1.toString() + " != " + e2.toString();
        if(op == ">") s = e1.toString() + " > " + e2.toString();
        if(op == ">=") s = e1.toString() + " >= " + e2.toString();
        return s;
    }
}
