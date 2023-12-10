package model.statement;

import collections.IHeap;
import collections.MyIDictionary;
import collections.MyIList;
import collections.MyIStack;
import exception.UndefinedState;
import model.PrgState;
import model.expression.Exp;
import model.value.Value;

public class PrintStmt implements IStmt{
    private Exp exp;
    public PrintStmt(Exp exp)
    {
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception
    {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIList<Value> output = state.getOut();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Integer, Value> heap = state.getHeap();

        output.add(exp.evaluate(symbolTable, heap));
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new PrintStmt(exp);
    }

    @Override
    public String toString()
    {
        return "print(" + exp.toString() + ")";
    }
}
