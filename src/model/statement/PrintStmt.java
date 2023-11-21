package model.statement;

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

        output.add(exp.evaluate(symbolTable));
        return state;
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
