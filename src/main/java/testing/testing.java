package testing;
import Domain.Exp.ValueExp;
import Domain.Exp.VarExp;
import Domain.PrgState;
import Domain.Statement.*;
import Domain.Type.IntType;
import Domain.Type.StringType;
import Domain.Value.StringValue;
import Repo.Repository;
import Controller.Controller;

public class testing {
    static public void main(String[] args){
        IStmt ex4 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new openRFile(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new readFile(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new readFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new closeRFile(new VarExp("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        PrgState prg1 = new PrgState(ex4, "test.txt");
        Repository repo1 = new Repository(prg1);
        Controller ctrl1 = new Controller(repo1);
        try {
            ctrl1.allStep();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
