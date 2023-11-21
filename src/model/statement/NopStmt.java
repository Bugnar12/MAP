package model.statement;

import model.PrgState;

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
    public String toString() {
        return "Empty statement!";
    }
}
