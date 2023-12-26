package Domain.Statement;

import Domain.*;
import Domain.Exp.Exp;
import Domain.Type.BoolType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.Value;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS;
    IStmt elseS;
    public IfStmt(Exp exp, IStmt thenS, IStmt elseS){
        this.exp=exp;
        this.thenS=thenS;
        this.elseS=elseS;
    }
    public String toString(){return "(IF("+exp.toString()+")THEN("+thenS.toString()+")ELSE("+elseS.toString()+"))";}
    public PrgState execute(PrgState state) throws MyException {
        //verify if the first expression evaluates to true if it does execute if statement if it evaluates to false execute the else statement, if it is an int throw exception
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String, Value> symTbl=state.getSymTable();
        Value val=exp.eval(symTbl,state.getHeap());
        if(val.getType().equals(new BoolType())){
            if(val.equals(new BoolValue(true))){
                stk.push(thenS);
            }
            else{
                stk.push(elseS);
            }
        }
        else throw new MyException("the condition of IF has not the type bool");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(),thenS.deepCopy(),elseS.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if(typexp.equals(new BoolType())){
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new MyException("the condition of IF has not the type bool");
    }
}
