package repository;

import exception.InvalidRepositroy;
import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

public class Repository implements IRepository{
    private List<PrgState> programs;
    private String logFilePath;

    public Repository(String logFilePath)
    {
        this.programs = new ArrayList<>();
        this.logFilePath = logFilePath;
        try{
            clearFile();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public Repository(List<PrgState> prgStates, String logFilePath) {
        this.programs = prgStates;
        this.logFilePath = logFilePath;
        try {
            clearFile();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
//    @Override
//    public PrgState getCurrentProgram() {
//        return programs.get(0);
//    }

    @Override
    public void add(PrgState program) {
        programs.add(program);
    }

    @Override
    public void logPrgStateExec(PrgState program) throws InvalidRepositroy
    {
        //if we declare PrintWriter() here, then in the try-catch block no error can be thrown
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(program.toString()); //put contents in the file
            logFile.close(); //file => needs to be closed
        }
        catch(IOException exception)
        {
            throw new InvalidRepositroy("Error opening the file!");
        }
    }

    @Override
    public void clearFile() throws Exception
    {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.println(""); //put contents in the file
            logFile.close(); //file => needs to be closed
        }
        catch(IOException exception)
        {
            throw new InvalidRepositroy("Error when opening the file!\n");
        }
    }

    @Override
    public List<PrgState> getProgramList() {
        return this.programs;
    }

    @Override
    public void setProgramList(List<PrgState> programs) {
        this.programs = programs;
    }

    @Override
    public String toString() {
        return "Repository{" + programs + "}";
    }
}
