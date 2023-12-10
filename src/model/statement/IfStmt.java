package model.statement;

import collections.IHeap;
import collections.MyIDictionary;
import collections.MyIList;
import collections.MyIStack;
import exception.InvalidType;
import model.PrgState;
import model.expression.Exp;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;

public class IfStmt implements IStmt{
    private Exp expression;
    private IStmt thenStatement;
    private IStmt elseStatement;

    public IfStmt(Exp expression, IStmt thenStatement, IStmt elseStatement)
    {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIList<Value> output = state.getOut();
        IHeap<Integer, Value> heap = state.getHeap();

        Value conditionalExpressionValue = expression.evaluate(symbolTable, heap);
        if(!conditionalExpressionValue.getType().equals(new BoolType()))
            throw new InvalidType("The type of the conditional expr. must be boolean!");
        if(((BoolValue)conditionalExpressionValue).getVal()) //if the cond. is true
            stack.push(thenStatement);
        else //the else is true
            stack.push(elseStatement);

        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new IfStmt(expression, thenStatement, elseStatement);
    }

    @Override
    public String toString() {
        return "(IF("+ expression.toString()+") THEN(" +thenStatement.toString()
                +")ELSE("+elseStatement.toString()+"))";
    }
}
