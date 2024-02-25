package model.statement;

import collections.MyIDictionary;
import collections.MyIStack;
import model.PrgState;
import model.type.Type;

public class CompStmt implements IStmt{
    private IStmt firstStatement;
    private IStmt secondStatement;

    public CompStmt(IStmt first, IStmt second)
    {
        this.firstStatement = first;
        this.secondStatement = second;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIStack<IStmt> stk = state.getExeStack();

        stk.push(secondStatement);
        stk.push(firstStatement);

        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new CompStmt(firstStatement, secondStatement);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        return secondStatement.typeCheck(firstStatement.typeCheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + firstStatement.toString() + ";" + secondStatement.toString() + ")";
    }

}
