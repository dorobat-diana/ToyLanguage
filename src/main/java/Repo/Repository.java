package Repo;

import Domain.MyException;
import Domain.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Repository implements RepoInterface {

    ArrayList<PrgState> programstates;
    int currentpos=0;

    public Repository(PrgState prg) {
        programstates = new ArrayList<PrgState>();
        programstates.add(prg);

    }


    public void logPrgStateExec(PrgState prg) throws MyException {

        try {
            String logfile=prg.getLogFilePath();
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logfile, true)));
            logFile.println(prg.toString());
            logFile.close();
        } catch ( IOException e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public ArrayList<PrgState> getPrgList() {
        return programstates;
    }

    @Override
    public void setPrgList(ArrayList<PrgState> prgList) {
        programstates = prgList;
    }
}
