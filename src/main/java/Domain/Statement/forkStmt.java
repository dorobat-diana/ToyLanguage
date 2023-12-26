package Domain.Statement;

import Domain.*;
import Domain.Type.Type;
import Domain.Value.Value;

public class forkStmt implements IStmt{
    IStmt Stmt;
    public forkStmt(IStmt Stmt){
        this.Stmt=Stmt;
    }
    public String toString(){return "fork("+Stmt.toString()+")";}
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStk=new MyStack<>();
        MyIDictionary<String, Value> symTbl=state.getSymTable();
        MyIDictionary<String, Value> newSymTbl=symTbl.deepCopy();
        PrgState newPrgState=new PrgState(newStk,newSymTbl,state.getOut(),state.getFileTable(),state.getHeap(),this.Stmt,state.getLogFilePath());
        return newPrgState;

    }

    @Override
    public IStmt deepCopy() {
        return new forkStmt(Stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
