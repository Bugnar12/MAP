package model.statement;

import collections.IHeap;
import collections.MyIDictionary;
import collections.MyIList;
import collections.MyIStack;
import exception.ExistingVariable;
import exception.InvalidType;
import model.PrgState;
import model.expression.Exp;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;

public class OpenReadFile implements IStmt{
    private Exp expression;

    public OpenReadFile(Exp expression)
    {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Value> symbolTable= state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, Value> heap = state.getHeap();

        Value filePath = this.expression.evaluate(symbolTable, heap);

        if(fileTable.isDefined((StringValue)(filePath)))
        {
            throw new ExistingVariable("The variable is already defined in the fileTable!\n");
        }
        try{
            BufferedReader buffer = new BufferedReader(new FileReader(((StringValue)filePath).getValue())); //here will be inserted the name of the file
            fileTable.put((StringValue)filePath, buffer);
        }
        catch(FileNotFoundException e){
            throw new FileNotFoundException(e.getMessage());
        }
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new OpenReadFile(expression);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type type = this.expression.typeCheck(typeEnv);
        if(!type.equals(new StringType()))
            throw new InvalidType("OpenReadFile : type of expression should be StringType!\n");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "OpenReadFile(" +
                expression +
                ")\n";
    }
}
