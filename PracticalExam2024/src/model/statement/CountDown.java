package model.statement;

import collections.IHeap;
import collections.ILatchTable;
import collections.MyIDictionary;
import exception.UndefinedVariable;
import model.PrgState;
import model.expression.ValueExp;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDown implements IStmt{
    private String variableName;
    private static Lock myLock = new ReentrantLock();

    public CountDown(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        myLock.lock();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
        ILatchTable latchTable = state.getLatchTable();
        if(symbolTable.isDefined(variableName))
        {
            IntValue evaluatedValue = (IntValue) symbolTable.lookUp(variableName);
            int integerValue = evaluatedValue.getVal();
            if(latchTable.containsKey(integerValue))
            {
                if(latchTable.get(integerValue) > 0) {

                    //decrement the value of the latch table at each countDown
                    latchTable.update(integerValue, latchTable.get(integerValue) - 1);
                }
                //we print the "child" as the problem states if the value is = 0
                state.getExeStack().push(new PrintStmt(new ValueExp(new IntValue(state.getId()))));
            }
            else
                throw new UndefinedVariable("The key is not defined in the latch table! CountDown\n");
        }
        else
            throw new UndefinedVariable("The variable is not defined in the symbol table! CountDown\n");

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
            throw new UndefinedVariable("Variable not defined! -CountDown typeCheck");
    }

    @Override
    public String toString() {
        return "CountDown{" +
                "variableName='" + variableName + '\'' +
                '}';
    }
}
