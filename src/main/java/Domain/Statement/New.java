package Domain.Statement;

import Domain.*;
import Domain.Exp.Exp;
import Domain.Type.RefType;
import Domain.Type.Type;
import Domain.Value.RefValue;
import Domain.Value.Value;

public class New implements IStmt {
    String var_name;
    Exp experssion;

    public New(String var_name, Exp expression) {
        this.var_name = var_name;
        this.experssion = expression;
    }

    public String toString() {
        return "new("+var_name + "->" + experssion.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer,Value> heap= state.getHeap();
        if(!symTbl.isDefined(var_name)){
            throw new MyException("the used variable is not defined");
        }
        else{
            Type varType=symTbl.lookup(var_name).getType();
            Value val=experssion.eval(symTbl, state.getHeap());
            if (varType instanceof RefType) {
                Type locationType = ((RefType) varType).getInner(); // Get the inner type of the variable

                if (!val.getType().equals(locationType)) {
                    throw new MyException("Type of value and the location type associated with the variable name are not the same");
                } else {
                    heap.add(val);
                    Value refValue_new = new RefValue(heap.get_possition(), locationType);
                    symTbl.update(var_name, refValue_new);
                }
            } else {
                Type typ=symTbl.lookup(var_name).getType();
                if(!val.getType().equals(typ)){
                    throw new MyException("type of value and the type associated to the variable name are not the same");
                }
                else{
                    heap.add(val);
                    Value refValue_new=new RefValue(heap.get_possition(),typ);
                    symTbl.update(var_name,refValue_new);
                }
            }
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new New(var_name, experssion);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar=(Type)typeEnv.lookup(var_name);
        Type typexp=experssion.typecheck(typeEnv);
        if(typevar instanceof RefType){
            RefType reft=(RefType)typevar;
            while(reft.getInner() instanceof RefType){
                reft=(RefType)reft.getInner();
            }
            if(typexp.equals(reft.getInner())){
                return typeEnv;
            }
            else throw new MyException("the types aren't the same1"+typexp.toString()+" "+reft.getInner().toString());
        }
        else {
            if (typevar.equals(typexp)) {
                return typeEnv;
            } else throw new MyException("the types aren't the same2");
        }
        }
    }

