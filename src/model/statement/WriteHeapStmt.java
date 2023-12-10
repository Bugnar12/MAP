package model.statement;

import collections.IHeap;
import collections.MyIDictionary;
import exception.InvalidType;
import exception.UndefinedVariable;
import model.PrgState;
import model.expression.Exp;
import model.type.RefType;
import model.value.RefValue;
import model.value.Value;

import java.sql.Ref;

public class WriteHeapStmt implements IStmt{
    private String varName;
    private Exp expression;

    public WriteHeapStmt(String varName, Exp expression)
    {
        this.varName = varName;
        this.expression = expression;
    }
    //this updates the Value from the heap table with the evaluated expression
    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if(!(symbolTable.isDefined(varName)))
            throw new UndefinedVariable("Variable not declared in symbol table!");
        Value symTblValue = symbolTable.lookUp(varName);
        if(!(symTblValue instanceof RefValue))
            throw new InvalidType("The type should be RefType!");
        RefValue refValueST = (RefValue) symTblValue;
        if(!(heap.containsKey(refValueST.getAddress())))
            throw new UndefinedVariable("Varibale not declared in the heap!");

        Value evaluatedExpression = this.expression.evaluate(symbolTable, heap);
        if(! (evaluatedExpression.getType().equals(((RefType) symTblValue.getType()).getInner())))
            throw new InvalidType("Eval. exp. and the inner type of the symTbl Value should have the same type!");

        heap.update(((RefValue) symTblValue).getAddress(), evaluatedExpression);
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new WriteHeapStmt(this.varName, this.expression);
    }

    @Override
    public String toString() {
        return "writeHeap(" + this.varName + ", " + this.expression.toString() + ")";
    }
}
