package Domain.Statement;

import Domain.MyException;
import Domain.MyIDictionary;
import Domain.MyIStack;
import Domain.PrgState;
import Domain.Type.Type;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt snd;
    public CompStmt(IStmt first,IStmt snd){
        this.first=first;
        this.snd=snd;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(),snd.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return snd.typecheck(first.typecheck(typeEnv));
    }

    public String toString(){
        return "("+first.toString()+";"+snd.toString()+")";
    }
}
