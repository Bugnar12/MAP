package model.statement;

import collections.IHeap;
import collections.MyIDictionary;
import collections.MyIList;
import collections.MyIStack;
import model.PrgState;
import model.expression.Exp;
import model.type.Type;
import model.value.Value;

public class PrintStmt implements IStmt{
    private Exp expression;
    public PrintStmt(Exp exp)
    {
        this.expression = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception
    {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIList<Value> output = state.getOut();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Integer, Value> heap = state.getHeap();

        output.add(expression.evaluate(symbolTable, heap));
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new PrintStmt(expression);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "print(" + expression.toString() + ")";
    }
}
