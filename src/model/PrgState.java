package model;

import collections.MyIDictionary;
import collections.MyIList;
import collections.MyIStack;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.nio.Buffer;


/*The program state contains an execution stack, a symbol table and an output
* */
public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symbolTable;
    MyIList<Value> out;
    IStmt originalPrg;
    MyIDictionary<StringValue, BufferedReader> fileTable;

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, Value> symtbl, MyIList<Value> o, MyIDictionary<StringValue, BufferedReader> fileTable, IStmt prg)
    {
        this.exeStack = stack;
        this.symbolTable = symtbl;
        this.out = o;
        this.fileTable = fileTable;
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


    @Override
    public String toString() {
        return "Program state :" +
                "\nexeStack :" + exeStack +
                "\nsymbolTable :" + symbolTable +
                "\noutput :" + out +
                "\nfileTable :" + fileTable + "\n";
    }
}
