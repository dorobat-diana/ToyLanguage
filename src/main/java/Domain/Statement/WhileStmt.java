package Domain.Statement;

import Domain.Exp.Exp;
import Domain.MyException;
import Domain.MyIDictionary;
import Domain.MyIStack;
import Domain.PrgState;
import Domain.Type.BoolType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.Value;

public class WhileStmt implements IStmt{
    Exp exp;
    IStmt statement;
    public WhileStmt(Exp exp, IStmt statement){
        this.exp=exp;
        this.statement=statement;
    }
    public String toString(){
        return "while("+exp.toString()+"):\n"+statement.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String, Value> symTbl=state.getSymTable();
        Value val=exp.eval(symTbl,state.getHeap());
        if(val.getType().equals(new BoolType())) {
            if (val.equals(new BoolValue(true))) {
                stk.push(this.deepCopy());
                stk.push(statement);
            }
        }
        else throw new MyException("the condition did not evaluate to a bool type");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(),statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if(typexp.equals(new BoolType())){
            statement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new MyException("the condition of WHILE has not the type bool");
    }
}
