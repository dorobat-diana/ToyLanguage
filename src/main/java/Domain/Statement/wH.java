package Domain.Statement;

import Domain.Exp.Exp;
import Domain.MyException;
import Domain.MyIDictionary;
import Domain.MyIHeap;
import Domain.PrgState;
import Domain.Type.RefType;
import Domain.Type.Type;
import Domain.Value.Value;

public class wH implements IStmt {
    String var_name;
    Exp expression;

    public wH(String var_name, Exp expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val;
        if (symTbl.isDefined(var_name)) {
            Value v = symTbl.lookup(var_name);
            if (v.getType().equals(new RefType())) {
                int address = (int) v.getvalue();
                if (heap.isDefined(address)) {
                    val = expression.eval(symTbl, heap);
                    Type type_ex = val.getType();
                    //I need the type of the variable that v points to
                    Value v2 = heap.lookup(address);
                    Type type_var = v2.getType();
                    if (type_ex.equals(type_var)) {
                        heap.update(address, val);
                    } else {
                        throw new MyException("the types aren't the same"+type_var.toString()+" "+type_ex.toString());
                    }
                } else {
                    throw new MyException("the address doesn't appear in heap");
                }
            } else {
                throw new MyException("the expression doesn't evaluate to RefType");
            }
        } else {
            throw new MyException("the variable is not defined");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new wH(var_name, expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar=(Type)typeEnv.lookup(var_name);
        Type typexp=expression.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp))){
            return typeEnv;
        }
        else throw new MyException("the types aren't the same");
    }

    @Override
    public String toString() {
        return "wH(" + var_name + "->" + expression.toString() + ")";
    }
}
