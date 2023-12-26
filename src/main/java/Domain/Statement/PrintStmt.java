package Domain.Statement;

import Domain.*;
import Domain.Exp.Exp;
import Domain.Type.Type;
import Domain.Value.Value;

public class PrintStmt implements IStmt {
    Exp exp;
    public PrintStmt(Exp exp){
        this.exp=exp;
    }
    public String toString(){
        return "print("+exp.toString()+")";
    }
    public PrgState execute(PrgState state) throws MyException {
        //get out and add exp to out
        MyIList<Value> out=state.getOut();
        MyIDictionary<String,Value> symTbl=state.getSymTable();
        out.add(exp.eval(symTbl,state.getHeap()));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}
