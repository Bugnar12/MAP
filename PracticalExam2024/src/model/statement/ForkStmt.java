package model.statement;

import collections.*;
import model.PrgState;
import model.type.Type;
import model.value.Value;

public class ForkStmt implements IStmt{
    private IStmt statement;

    public ForkStmt(IStmt statement)
    {
        this.statement = statement;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Value> dictionary = state.getSymbolTable();
        MyIDictionary<String, Value> dictionaryCopy = dictionary.copy();
        MyIStack<IStmt> stackFork = new MyStack<>();

        //TODO : why the program does not work properly when including this line?
        //Answer : because we need to create a new EMPTY stack for the second program that will get executed when fork() runs
        //stackFork.push(this.statement);

        return new PrgState(stackFork, dictionaryCopy, state.getOut(), state.getFileTable(),
                state.getHeap(), state.getLatchTable(), this.statement);

    }
    @Override
    public IStmt deepcopy() {
        return new ForkStmt(this.statement);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        statement.typeCheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Fork( " + this.statement.toString() + " )";
    }
}
