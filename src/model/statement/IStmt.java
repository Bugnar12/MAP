package model.statement;

import collections.MyIDictionary;
import exception.UndefinedState;
import model.PrgState;
import model.type.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws Exception;
    IStmt deepcopy();
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception;

}
