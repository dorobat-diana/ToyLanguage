package View;

import Domain.Exp.*;
import Domain.PrgState;
import Domain.Statement.*;
import Domain.Type.BoolType;
import Domain.Type.IntType;
import Domain.Type.RefType;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Repo.Repository;
import Controller.*;

public class Interpreter {
    static public void main(String[] args) {
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
        // Example 4: Rational expression
        IStmt ex4 = new CompStmt(new VarDeclStmt("x", new IntType()),
                new CompStmt(new VarDeclStmt("y", new IntType()),
                        new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(5))),
                                new CompStmt(new AssignStmt("y", new ValueExp(new IntValue(10))),
                                        new PrintStmt(new RationalExp(new VarExp("x"), new VarExp("y"), ">"))))));
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntType()),new CompStmt( new New("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                        new CompStmt(new New("a", new VarExp("v")),
                                new CompStmt(new New("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new rH(new rH(new VarExp("a")))))))));
        IStmt ex6= new CompStmt(new VarDeclStmt("v",new IntType()),new CompStmt(new AssignStmt("v",new ValueExp
                (new IntValue(4))),new CompStmt(new WhileStmt(new RationalExp(new VarExp("v"),new ValueExp(new
                IntValue(0)),">"),new CompStmt(new PrintStmt(new VarExp("v")),new AssignStmt("v",new
                ArithExp('-',new VarExp("v"),new ValueExp(new IntValue(1)))))),new PrintStmt(new VarExp("v")))));
        PrgState prg4 = new PrgState(ex4, "prg4.txt");
        Repository repo4 = new Repository(prg4);
        Controller ctrl4 = new Controller(repo4);
        PrgState prg1 = new PrgState(ex1, "prg1.txt");
        Repository repo1 = new Repository(prg1);
        Controller ctrl1 = new Controller(repo1);
        PrgState prg2 = new PrgState(ex2, "prg2.txt");
        Repository repo2 = new Repository(prg2);
        Controller ctrl2 = new Controller(repo2);
        PrgState prg3 = new PrgState(ex3, "prg3.txt");
        Repository repo3 = new Repository(prg3);
        Controller ctrl3 = new Controller(repo3);
        PrgState prg5 = new PrgState(ex5,"prg5.txt");
        Repository repo5 = new Repository(prg5);
        Controller ctrl5 = new Controller(repo5);
        PrgState prg6=new PrgState(ex6, "prg6.txt");
        Repository repo6=new Repository(prg6);
        Controller ctrl6 = new Controller(repo6);
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctrl1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctrl2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctrl3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctrl4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctrl5));
        menu.addCommand(new RunExample("6",ex6.toString(),ctrl6));
        menu.show();
    }
}
