package Domain.Statement;

import Domain.MyException;
import Domain.MyIDictionary;
import Domain.PrgState;
import Domain.Type.Type;

public class NopStmt implements IStmt {
    NopStmt(){}
    public String toString(){
        return "";
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
