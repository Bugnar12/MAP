package controller;

import collections.Heap;
import collections.IHeap;
import collections.MyIStack;
import exception.InvalidRepositroy;
import model.PrgState;
import model.expression.Exp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.expression.readHeapExp;
import model.statement.*;
import model.type.IntType;
import model.type.RefType;
import model.value.IntValue;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public IRepository getRepo() {
        return this.repo;
    }

    public void setRepo(IRepository repo) {
        this.repo = repo;
    }

    public Controller(IRepository repo) {
        this.repo = repo;
    }

//    public PrgState oneStep(PrgState state) throws Exception {
//        MyIStack<IStmt> stack = state.getExeStack();
//        if (stack.isEmpty())
//            throw new InvalidRepositroy("The stack is empty!");
//
//        IStmt currentStmt = stack.pop();
//        return currentStmt.execute(state);
//    }

    private List<Integer> getAddrFromSymbolTable(Collection<Value> symbolTableValues) {
        //return the list of all RefValue addresses from the symbol table
        //creates a stream of Stream<Value>
        return symbolTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    //:: method reference operator used, behave like lambda expression with the help of the class directly ; can refer static methods, instances, void etc.
    //-> : lambda expression : separates parameters from the actual implementation
    private List<Integer> getAddrFromHeapTable(Collection<Value> heapTableValues) {
        return heapTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey())) //filter object that match the certain predicate
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    //----VERSION 1 OF SAFE GARBAGE COLLECTOR----
    List<Integer> getAllAddresses(Collection<Value> symbolTableValues, IHeap<Integer, Value> heapTable) {
        ConcurrentLinkedDeque<Integer> symbolTableAddresses = symbolTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

        System.out.println("Symbol table addresses : " + symbolTableAddresses);
        symbolTableAddresses.stream()
                .forEach(a -> {
                    Value v = heapTable.getContent().get(a);
                    if (v instanceof RefValue)
                        if (!symbolTableAddresses.contains(((RefValue) v).getAddress()))
                            symbolTableAddresses.add(((RefValue) v).getAddress());
                });
        System.out.println("Symbol table intersected with heap table : " + symbolTableAddresses);

        return symbolTableAddresses.stream().toList();
    }

    //----VERSION 2 OF SAFE GARBAGE COLLECTOR----
    private Map<Integer, Value> safeGarbageCollector2(List<Integer> symbolTableAddresses, List<Integer> heapReferencedAddresses, PrgState program)
    {
        IHeap<Integer, Value> heap = program.getHeap();
        return heap.getContent().entrySet().stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()) || heapReferencedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    public List<PrgState> removeCompletedProgram(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    void oneStepForAllPrograms(List<PrgState> programList) throws InterruptedException {
        programList.forEach(p -> {
            try {
                this.repo.logPrgStateExec(p);
            } catch (Exception exception) {
                exception.printStackTrace(); //printStackTrace() -> extended printing of the error occured(line + problem)
            }
        });
        //Callable -> can return something ; Runnable -> cannot return only execute
        List<Callable<PrgState>> callList = programList.stream()
                //() -> p.oneStep() : create a Callable() object that calls oneStep method of Repository.
                .map((PrgState p) -> (Callable<PrgState>) (() -> p.oneStep()))
                .collect(Collectors.toList());

        List<PrgState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException exception) {
                        exception.getStackTrace();
                        return null;
                    }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        programList.addAll(newProgramList);
        programList.forEach(program -> {
            try {
                repo.logPrgStateExec(program);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        repo.setProgramList(programList);
    }

    public void allStep() throws Exception
    {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programList = removeCompletedProgram(repo.getProgramList());
        while(programList.size() > 0)
        {
            Collection<Value> symbolTableAddresses = programList.stream()
                    .flatMap(program -> program.getSymbolTable().getContent().values().stream())
                    .collect(Collectors.toList());
            Collection<Value> heapTableAddresses = programList.get(0).getHeap().getContent().values();
            programList.get(0).getHeap().setContent(safeGarbageCollector2(getAddrFromSymbolTable(symbolTableAddresses), getAddrFromHeapTable(heapTableAddresses), programList.get(0)));

            oneStepForAllPrograms(programList);
            programList = removeCompletedProgram(repo.getProgramList());
        }
        executor.shutdown();
        repo.setProgramList(programList);


//            unsafe garbage collector
//            program.getHeap().setContent(unsafeGarbageCollector(
//                    getAddrFromSymbolTable(program.getSymbolTable().getAllValues()),
//                    program.getHeap().getContent()));

            //safe garbage collector v2
//            program.getHeap().setContent(safeGarbageCollector2(getAddrFromSymbolTable(program.getSymbolTable().getAllValues()),
//                    getAddrFromHeapTable(program.getHeap().getAllValues()), program));

            //System.out.println(program); //display the program states sequentially

    }

}
