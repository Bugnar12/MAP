package model;

import collections.*;
import exception.EmptyADT;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;


/*The program state contains an execution stack, a symbol table and an output
* */
public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symbolTable;
    private MyIList<Value> out;
    private IStmt originalPrg;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, Value> heap;
    private ILatchTable latchTable;
    private int id;
    private static int nextID = 1;

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, Value> symtbl, MyIList<Value> o, MyIDictionary<StringValue, BufferedReader> fileTable, IHeap<Integer, Value> heap, ILatchTable latchTable, IStmt prg)
    {
        this.exeStack = stack;
        this.symbolTable = symtbl;
        this.out = o;
        this.fileTable = fileTable;
        this.heap = heap;
        this.latchTable = latchTable;
        this.id = setNextID();
        this.originalPrg = prg.deepcopy();
        stack.push(prg);
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(MyIDictionary<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() { return fileTable; }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> newFileTable){ this.fileTable = newFileTable; }

    public IHeap<Integer, Value> getHeap() { return heap; }

    public void setHeap(IHeap<Integer, Value> newHeap) { this.heap = newHeap; }

    public ILatchTable getLatchTable(){ return latchTable; }

    public void setLatchTable(ILatchTable newLatchTable) {
        this.latchTable = newLatchTable;
    }

    public int getId() {
        return id;
    }
    public synchronized int setNextID()
    {
        return nextID++; // this will be passed to the id from our class
    }

    public Boolean isNotCompleted()
    {
        return !(this.exeStack.isEmpty());
    }

    public PrgState oneStep() throws Exception
    {
        if(exeStack.isEmpty())
            throw new EmptyADT("The execution stack is empty!");
        IStmt currentStmt = this.exeStack.pop();
        return currentStmt.execute(this);
    }
    @Override
    public String toString() {
        return "Program state :" +
                "\nID :" + id +
                "\nexeStack :" + exeStack +
                "\nsymbolTable :" + symbolTable +
                "\noutput :" + out +
                "\nfileTable :" + fileTable +
                "\nheap :" + heap + "\n";
    }
}
