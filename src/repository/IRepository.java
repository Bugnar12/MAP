package repository;

import model.PrgState;

public interface IRepository {
    PrgState getCurrentProgram();
    void add(PrgState program);
    void logPrgStateExec(PrgState program) throws Exception;
    void clearFile() throws Exception;
}
