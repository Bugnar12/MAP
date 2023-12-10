package model.statement;

import collections.IHeap;
import collections.MyIDictionary;
import collections.MyIStack;
import exception.InvalidType;
import exception.UndefinedVariable;
import model.PrgState;
import model.expression.Exp;
import model.type.RefType;
import model.value.RefValue;
import model.value.Value;

public class NewStmt implements IStmt{
    private String variableName;
    private Exp expression;

    public NewStmt(String variableName, Exp expression)
    {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if (!symbolTable.isDefined(variableName))
            throw new UndefinedVariable("Variable not declared in SymbolTable!");

        Value getVariableSymTbl = symbolTable.lookUp(variableName);

        if (!(getVariableSymTbl.getType() instanceof RefType))
            throw new InvalidType("The type of the variable should be RefType!");

        Value evaluatedExpression = expression.evaluate(symbolTable, heap);
        //System.out.println(evaluatedExpression);

        //check if the type of the evaluated expression equals the INNER TYPE of the RefValue from the symbol table
        if (!(evaluatedExpression.getType().equals(((RefType) getVariableSymTbl.getType()).getInner())))
        {
            throw new InvalidType("The type of the exp. should be the same as that of the inner type value!");
        }
        int getFreePosition = heap.getFirstFreeAddress();
        heap.put(getFreePosition, evaluatedExpression);

        //TL;DR : update the address from the symbol table correspondingly do the first free position generate from the heap table
        symbolTable.update(variableName, new RefValue(getFreePosition, ((RefType) getVariableSymTbl.getType()).getInner()));
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new NewStmt(variableName, expression);
    }

    @Override
    public String toString() {
        return "new(" + this.variableName + ", " + this.expression.toString() + ")";
    }
}
