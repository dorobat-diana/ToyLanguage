package Domain;

import Domain.Statement.IStmt;
import Domain.Value.Value;

import java.io.BufferedReader;

public class PrgState {
    MyIStack<IStmt> stk;
    MyIDictionary<String, Value> symTbl;
    MyIList<Value> out;
    MyIHeap<Integer, Value> heap;
    MyIFileTable<String, BufferedReader> fileTable;
    IStmt originalProgram;
    String logFilePath;
    private static int currentId=0;
    private final int id;
    public PrgState(IStmt prg, String logFilePath){
        stk= new MyStack<>();
        symTbl= new MyDictionary<>();
        out= new MyList<>();
        fileTable=new MyFileTable();
        heap=new MyHeap<>();
        originalProgram=prg.deepCopy();
        stk.push(prg);
        this.logFilePath=logFilePath;
        this.id=getNewId();
    }
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symTbl, MyIList<Value> out, MyIFileTable<String,BufferedReader> fileTable, MyIHeap<Integer,Value> heap, IStmt prg, String logFilePath){
        this.stk=stk;
        this.symTbl=symTbl;
        this.out=out;
        this.fileTable=fileTable;
        this.heap=heap;
        this.originalProgram=prg.deepCopy();
        this.id = getNewId();
        stk.push(prg);
        this.logFilePath=logFilePath;
    }
    public static synchronized int getNewId(){
        currentId++;
        return currentId;
    }
    public IStmt get_originalProgram(){
        return originalProgram;
    }

    public MyIHeap<Integer,Value> getHeap(){return heap;}

    public MyIStack<IStmt> getStk() {
        return stk;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTbl;
    }

    public MyIList<Value> getOut() {
        return out;
    }
    public MyIFileTable<String, BufferedReader> getFileTable(){
        return fileTable;
    }

    public void setHeap(MyIHeap<Integer,Value> heap){this.heap=heap;}
    public void setStk(MyIStack<IStmt> stk){
        this.stk=stk;
    }
    public void setSymTable(MyIDictionary<String, Value> symTbl){
        this.symTbl=symTbl;
    }
    public void setOut(MyIList<Value> out){
        this.out=out;
    }
    public void setFileTable(MyIFileTable<String,BufferedReader> fileTable){
        this.fileTable=fileTable;
    }
    @Override
    public String toString(){
        return "Program State ID:"+id+"\n"+"ExeStack:\n"+stk.toString()+"\nSymTable:\n"+symTbl.toString()+"\nOut:\n"+out.toString()+"\n"+"FileTable:\n"+fileTable.toString()+"\n"+heap.toString()+"\n";
    }
    public String getLogFilePath(){
        return logFilePath;
    }
    public boolean isNotCompleted(){
        return !stk.isEmpty();
    }
    public PrgState oneStep() throws MyException {
        if(stk.isEmpty())
            throw new MyException("prgstate stack is empty");
        IStmt crtStmt=stk.pop();
        return crtStmt.execute(this);
    }

    public Object getExeStack() {
        return stk;
    }
    public int getID(){
        return id;
    }
}
