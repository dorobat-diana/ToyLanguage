package Controller;

import Domain.Statement.IStmt;
import Domain.MyException;
import Domain.MyIStack;
import Domain.PrgState;
import Domain.Value.Value;
import Repo.RepoInterface;
import Domain.Value.RefValue;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value>
            heap){
        //we have to verify every entry from heap if it is in symTableAddr, but we also have to verify the values from heap
        //if they are RefValues
        //here I don't want the one that point to a ref value but the ref value address that is pointed to
        List<Integer> addr = heap.entrySet().stream()
                .filter(e->e.getValue() instanceof RefValue)
                .map(e->(RefValue)e.getValue())
                .map(add->(int)add.getvalue())
                .collect(Collectors.toList());
        //combine symtableadrr and addr
        List<Integer> combinedAddresses = Stream.concat(symTableAddr.stream(), addr.stream())
                .distinct() // Remove duplicates
                .collect(Collectors.toList());
        return heap.entrySet().stream()
                .filter(e->combinedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return (int)v1.getvalue();})
                .collect(Collectors.toList());
    }
    RepoInterface repo;
    ExecutorService executor;

    public void allStep() throws MyException {
        executor = java.util.concurrent.Executors.newFixedThreadPool(2);
        ArrayList<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0){
            try {
                unsafeGarbageCollector(getAddrFromSymTable(prgList.stream().map(p->p.getSymTable().getContent().values()).toList().stream().flatMap(Collection::stream).collect(Collectors.toList())),
                        prgList.get(0).getHeap().getContent());
                oneStepForAllPrg(prgList);
            } catch (InterruptedException e) {
                throw new MyException(e.getMessage());
            }
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }
    public Controller(RepoInterface r) {
        repo = r;
    }
    public void display() {
        ArrayList<PrgState> prgList = repo.getPrgList();
        System.out.println(prgList.get(0));
    }
    public ArrayList<PrgState> removeCompletedPrg(ArrayList<PrgState> inPrgList){
        return (ArrayList<PrgState>)inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }
    public void oneStepForAllPrg(ArrayList<PrgState> prgList) throws MyException, InterruptedException {
        //before the execution, print the PrgState List into the log file
        try{
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException | IOException e) {
                throw new RuntimeException(e);
            }

        });}
        catch (RuntimeException e){
            throw new MyException(e.getMessage());
        }
        //RUN concurrently one step for each of the existing PrgStates
        //prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) p::oneStep)
                .collect(Collectors.toList());
        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            prgList.addAll(newPrgList);
        }
        catch (RuntimeException e){
            throw new MyException(e.getMessage());
        }
        //add the new created threads to the list of existing threads
        //after the execution, print the PrgState List into the log file
        try{prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException | MyException e) {
                throw new RuntimeException(e);
            }
        });}
        catch (RuntimeException e){
            throw new MyException(e.getMessage());
        }
        //save the current programs in the repository
        repo.setPrgList(prgList);
    }

    public void oneStep1() throws MyException {
        executor = java.util.concurrent.Executors.newFixedThreadPool(2);
        ArrayList<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        if(prgList.size() > 0){
            try {
                unsafeGarbageCollector(getAddrFromSymTable(prgList.stream().map(p->p.getSymTable().getContent().values()).toList().stream().flatMap(Collection::stream).collect(Collectors.toList())),
                        prgList.get(0).getHeap().getContent());
                oneStepForAllPrg(prgList);
                //print into the console all prgstates
                prgList.forEach(prg -> {
                    try {
                        System.out.println(prg);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (InterruptedException | MyException e) {
                throw new MyException(e.getMessage());
            }
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }
    public ArrayList<PrgState> getPrgList(){
        return repo.getPrgList();
    }
    public List<Value> getoutList(){
        return repo.getPrgList().get(0).getOut().getContent();
    }
}
