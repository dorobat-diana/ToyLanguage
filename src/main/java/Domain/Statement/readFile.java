package Domain.Statement;

import Domain.*;
import Domain.Exp.Exp;
import Domain.Type.IntType;
import Domain.Type.StringType;
import Domain.Type.Type;
import Domain.Value.IntValue;
import Domain.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStmt{
    private Exp exp;
    private String var_name;
    public readFile(Exp exp, String var_name){
        this.exp=exp;
        this.var_name=var_name;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIFileTable<String, BufferedReader> fileTable = state.getFileTable();
        if(symTbl.isDefined(this.var_name)){
            Value val=symTbl.lookup(this.var_name);
            if(val.getType().equals(new IntType())){
                try {
                    Value evaluated = exp.eval(symTbl, state.getHeap());
                    if(evaluated.getType().equals(new StringType())){
                        if(!fileTable.isDefined((String) evaluated.getvalue()))
                            throw new MyException("The file is not defined");
                        BufferedReader buf=fileTable.getFileDescriptor((String) evaluated.getvalue());
                        //reads a line from the file. If the line is null creates a zero int value, otherwise translates the returnes string into an int value (using integer.parseint(string))
                        String line=buf.readLine();
                        if(line==null)
                            symTbl.update(this.var_name,new IntValue(0));
                        else
                            symTbl.update(this.var_name,new IntValue(Integer.parseInt(line)));
                    }
                    else {
                        throw new MyException("The expression is not a string");
                    }
                }
                catch (MyException e){
                    throw new MyException("The expression is not an integer");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            else{
                throw new MyException("The variable is not an integer");
            }
        }
        else{
            throw new MyException("The variable is not defined");
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
        return "readFile("+exp.toString()+")";
    }
}
