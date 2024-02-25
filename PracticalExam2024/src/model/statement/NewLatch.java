package model.statement;

import collections.IHeap;
import collections.ILatchTable;
import collections.MyIDictionary;
import exception.InvalidType;
import exception.UndefinedVariable;
import model.PrgState;
import model.expression.Exp;
import model.type.IntType;
import model.type.Type;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import model.value.IntValue;
import model.value.Value;

public class NewLatch implements IStmt{
    private String variableName;
    private Exp expression;
    private static Lock myLock = new ReentrantLock();

    public NewLatch(String variableName, Exp expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        //we want this section to be accessed only by one thread at a time so we lock the execution
        myLock.lock();
        MyIDictionary<String, Value> symbolTable= state.getSymbolTable();
        IHeap<Integer, Value> heap = state.getHeap();
        ILatchTable latchTable = state.getLatchTable();
        Value resultingValue = this.expression.evaluate(symbolTable, heap);

        if(resultingValue.getType().equals(new IntType()))
        {
            IntValue resultingIntValue = (IntValue)resultingValue;
            int resultingInteger = resultingIntValue.getVal();

            int freeAddress = latchTable.getFreeAddress();
            latchTable.put(freeAddress, resultingInteger);

            if(symbolTable.isDefined(this.variableName))
            {
                symbolTable.update(this.variableName, new IntValue(freeAddress));
            }
            else {
                throw new UndefinedVariable("Variable " + this.variableName + " is not defined in symbol table! - NewLatch\n");
            }
        }
        else
        {
            throw new InvalidType("Expression cannot be evaluated to integer! - NewLatch\n");
        }
        //unlock the resource
        myLock.unlock();
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        if(typeEnv.lookUp(variableName).equals(new IntType())) {
            if (expression.typeCheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else
                throw new InvalidType("Expression cannot be evaluated to integer! - NewLatch\n");
        }
        else
            throw new InvalidType("NewLatch: expression is not of type int! - TypeCheck\n");
    }

    @Override
    public String toString() {
        return "NewLatch{" +
                "variableName='" + variableName + '\'' +
                ", expression=" + expression +
                '}';
    }
}
