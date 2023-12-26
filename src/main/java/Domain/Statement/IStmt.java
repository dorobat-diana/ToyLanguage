package Domain.Statement;

import Domain.MyException;
import Domain.MyIDictionary;
import Domain.PrgState;
import Domain.Type.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepCopy();
    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
