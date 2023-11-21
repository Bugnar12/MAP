package model.statement;

import exception.UndefinedState;
import model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws Exception;
    IStmt deepcopy();

}
