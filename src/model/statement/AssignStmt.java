package model.statement;

import collections.Heap;
import collections.IHeap;
import collections.MyIDictionary;
import collections.MyIStack;
import exception.InvalidType;
import exception.UndefinedVariable;
import model.PrgState;
import model.expression.Exp;
import model.value.Value;
import model.type.Type;


//fields will be final for deepCopy
public class AssignStmt implements IStmt{
    private final String id;
    private final Exp expression;
    public AssignStmt(String id, Exp exp)
    {
        this.id = id;
        this.expression = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if(symbolTable.isDefined(id))
        {
            Value val = expression.evaluate(symbolTable, heap);
            Type typeId = (symbolTable.lookUp(id)).getType();
            Type valType = val.getType();
            if(valType.equals(typeId))
                symbolTable.update(this.id, val);
            else
                throw new InvalidType("Declared type of variable" + id + " and type of the assigned expression don't match!");
        }
        else
            throw new UndefinedVariable("The used variable" + id + "was not declared before");

        return null;
    }

    //should create a new object of type AssignStmt and return it
    @Override
    public IStmt deepcopy() {
        return new AssignStmt(id, expression);
    }


    @Override
    public String toString() {
        return this.id+"="+ this.expression.toString();}


}
