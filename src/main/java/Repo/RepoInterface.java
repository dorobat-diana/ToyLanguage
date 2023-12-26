package Repo;

import Domain.MyException;
import Domain.PrgState;

import java.io.IOException;
import java.util.ArrayList;

public interface RepoInterface {
    public void logPrgStateExec(PrgState p) throws MyException, IOException;
    ArrayList<PrgState> getPrgList();
    void setPrgList(ArrayList<PrgState> prgList);
}
