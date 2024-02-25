package model.statement;

import collections.MyIDictionary;
import model.PrgState;
import model.type.Type;

public class NopStmt implements IStmt{
    public NopStmt() {}


    @Override
    public PrgState execute(PrgState state) throws Exception {
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new NopStmt();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        return null;
    }

    @Override
    public String toString() {
        return "Empty statement!";
    }
}
