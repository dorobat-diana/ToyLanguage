package Domain.Statement;

import Domain.Exp.Exp;
import Domain.MyException;
import Domain.MyIDictionary;
import Domain.MyIStack;
import Domain.PrgState;
import Domain.Type.StringType;
import Domain.Type.Type;
import Domain.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFile implements IStmt{
    Exp exp;
    public closeRFile(Exp exp){
        this.exp=exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        try {
            Value val = exp.eval(state.getSymTable(),state.getHeap());
            if(val.getType().equals(new StringType())){
                if(!state.getFileTable().isDefined((String) val.getvalue()))
                    throw new MyException("The file is not defined");
                BufferedReader buf=state.getFileTable().getFileDescriptor((String) val.getvalue());
                buf.close();
                state.getFileTable().remove((String) val.getvalue());
            }
            else {  throw new MyException("The expression is not a string"); }

        }
        catch (MyException e){
            throw new MyException("The expression is not an integer");
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeexp=exp.typecheck(typeEnv);
        if(typeexp.equals(new StringType())){
            return typeEnv;
        }
        else throw new MyException("the condition of IF has not the type bool");
    }

    @Override
    public String toString(){
        return "closeRFile("+exp.toString()+")";
    }
}
