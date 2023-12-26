package Domain.Statement;

import Domain.*;
import Domain.Type.*;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.Value;

public class VarDeclStmt implements IStmt {
    String name;
    Type typ;

    public VarDeclStmt(String name, Type typ) {
        this.name = name;
        this.typ = typ;
    }

    public String toString() {
        return typ.toString() + " " + name;
    }

    public PrgState execute(PrgState state) throws MyException {
        //verify if the variable is already declared if true the error, otherwise add it to table and set it to default value
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (!symTbl.isEmpty() && symTbl.isDefined(name)) {
            throw new MyException("Variable already declared");
        } else {
            if (typ.equals(new IntType())) {
                symTbl.add(name, typ.defaultValue());
            } else if (typ.equals(new BoolType())) {
                symTbl.add(name, typ.defaultValue());
            }
            else if(typ.equals(new StringType())){
                symTbl.add(name, typ.defaultValue());
            }
            else if(typ.equals(new RefType())){
                symTbl.add(name, typ.defaultValue());
            }
            else {
                throw new MyException("Invalid type");
            }
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, typ);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.add(name, typ);
        return typeEnv;
    }
}
