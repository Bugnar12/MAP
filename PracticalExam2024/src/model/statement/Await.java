package model.statement;

import collections.IHeap;
import collections.ILatchTable;
import collections.MyIDictionary;
import exception.InvalidType;
import exception.UndefinedVariable;
import model.PrgState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Await implements IStmt{
    private String variableName;
    private static Lock myLock = new ReentrantLock();

    public Await(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        myLock.lock();
        ILatchTable latchTable = state.getLatchTable();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
        if(symbolTable.isDefined(variableName))
        {
            Value evaluatedValue = symbolTable.lookUp(variableName);
            if(evaluatedValue.getType().equals(new IntType()))
            {
                IntValue evaluatedIntValue = (IntValue) evaluatedValue;
                int integerValue = evaluatedIntValue.getVal();

                if(latchTable.containsKey(integerValue))
                {
                    //if the index is found in the latch table, check if the value is 0 aka. the countdown is finished
                    if(latchTable.get(integerValue) != 0)
                        //if it is not 0, then the CountDownLatch is not finished yet and we must push the Await() back to the execution stack
                        state.getExeStack().push(this);
                }
                else
                    throw new UndefinedVariable("Latch table does not contain the key! Await\n");
            }
            else
                throw new InvalidType("The value should be of type int! Await\n");
        }
        else
            throw new UndefinedVariable("The variable is not defined in the symbolTable! Await\n");

        myLock.unlock();
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        if(typeEnv.lookUp(variableName).equals(new IntType()))
            return typeEnv;
        else
            throw new UndefinedVariable("The variable is not defined! Await-typeCheck\n");
    }

    @Override
    public String toString() {
        return "Await{" +
                "variableName='" + variableName + '\'' +
                '}';
    }
}
