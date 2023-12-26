package Domain.Statement;

import Domain.*;
import Domain.Exp.Exp;
import Domain.Type.StringType;
import Domain.Type.Type;
import Domain.Value.StringValue;
import Domain.Value.Value;

import java.io.BufferedReader;
import java.io.Reader;

public class openRFile implements IStmt{
    private Exp exp;
    public openRFile(Exp exp) {
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIFileTable<String,BufferedReader> fileTable = state.getFileTable();
        Value val = exp.eval(symTbl,state.getHeap());
        if(val.getType().equals(new StringType())){
            String string_val;
            string_val=(String)val.getvalue();
            if(fileTable.isDefined(string_val)){
                throw new MyException("The file is already opened");
            }
            else{
                try{
                    //open the file with the name in string_val
                    Reader reader = new java.io.FileReader(string_val);
                    BufferedReader br = new BufferedReader(reader);
                    fileTable.add(val.toString(),br);
                }
                catch (Exception e){
                    throw new MyException("The file does not exist");
                }
            }
        }
        else{
            throw new MyException("The expression is not a string");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if(typexp.equals(new StringType())){
            return typeEnv;
        }
        else throw new MyException("the condition of IF has not the type bool");
    }

    @Override
    public String toString(){
        return "openRFile("+exp.toString()+")";
    }
}
