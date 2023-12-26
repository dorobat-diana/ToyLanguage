package View;

import Domain.*;
import Domain.Exp.ArithExp;
import Domain.Exp.ValueExp;
import Domain.Exp.VarExp;
import Domain.Statement.*;
import Domain.Type.BoolType;
import Domain.Type.IntType;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Repo.*;
import Controller.*;
import Domain.Type.*;
import Domain.Exp.*;

public class View {
    static public void main(String[] args) throws MyException {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                                new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                                new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a))) add this statement
        //a should first be initialized as ref type to an int value
        IStmt ex4 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                        new CompStmt(new New("a", new VarExp("v")),
                                new CompStmt(new New("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new rH(new rH(new VarExp("a")))))))));
        // int v; Ref int a; v=10;new(a,22);
        // fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                        new CompStmt(new New("a", new ValueExp(new IntValue(22))),
                                new CompStmt(new forkStmt(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
                                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new rH(new VarExp("a"))))))),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new rH(new VarExp("a")))))))));
        IStmt ex;
        //use the textmenu and runexaple methods to run the program
        //use typechecker for every statement before creating the prgstate
        ex1.typecheck(new MyDictionary<String, Type>());
        ex2.typecheck(new MyDictionary<String, Type>());
        ex3.typecheck(new MyDictionary<String, Type>());
        ex4.typecheck(new MyDictionary<String, Type>());
        ex5.typecheck(new MyDictionary<String, Type>());
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), new Controller(new Repository(new PrgState(ex1, "prg1.txt")))));
        menu.addCommand(new RunExample("2", ex2.toString(), new Controller(new Repository(new PrgState(ex2, "prg2.txt")))));
        menu.addCommand(new RunExample("3", ex3.toString(), new Controller(new Repository(new PrgState(ex3, "prg3.txt")))));
        menu.addCommand(new RunExample("4", ex4.toString(), new Controller(new Repository(new PrgState(ex4, "prg4.txt")))));
        menu.addCommand(new RunExample("5", ex5.toString(), new Controller(new Repository(new PrgState(ex5, "prg5.txt")))));
        menu.show();

    }

}