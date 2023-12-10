package repository;

import model.PrgState;

import java.util.List;

public interface IRepository {
    //PrgState getCurrentProgram();
    void add(PrgState program);
    void logPrgStateExec(PrgState program) throws Exception;
    void clearFile() throws Exception;
    List<PrgState> getProgramList();
    void setProgramList(List<PrgState> programs);
}
