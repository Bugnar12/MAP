package controller;

import collections.MyIStack;
import exception.InvalidRepositroy;
import model.PrgState;
import model.statement.IStmt;
import repository.IRepository;

public class Controller {
    private IRepository repo;
    public IRepository getRepo()
    {
        return this.repo;
    }
    public void setRepo(IRepository repo)
    {
        this.repo = repo;
    }

    public Controller(IRepository repo)
    {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws Exception
    {
        MyIStack<IStmt> stack = state.getExeStack();
        if(stack.isEmpty())
            throw new InvalidRepositroy("The stack is empty!");

        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws Exception
    {
        PrgState program = repo.getCurrentProgram();
        repo.logPrgStateExec(program);
        while(!program.getExeStack().isEmpty())
        {
            oneStep(program);
            //System.out.println(program); //display the program states sequentially
            repo.logPrgStateExec(program);
        }
    }

}
