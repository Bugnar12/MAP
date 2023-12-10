package model.statement;

import collections.IHeap;
import collections.MyIDictionary;
import collections.MyIStack;
import exception.InvalidType;
import exception.UndefinedVariable;
import model.PrgState;
import model.expression.Exp;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class CloseReadFile implements IStmt{
    private Exp expression;
    public CloseReadFile(Exp expression)
    {
        this.expression = expression;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        //MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symbolTable= state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, Value> heap = state.getHeap();

        Value filePath = expression.evaluate(symbolTable, heap);
        Type filePathType = filePath.getType();
        if(!filePathType.equals(new StringType()))
            throw new InvalidType("The type of the filePath should be string!\n");


        BufferedReader buffer = fileTable.lookUp((StringValue)filePath);
        if(!fileTable.isDefined((StringValue)filePath))
            throw new UndefinedVariable("The filePath is not declared in the fileTable!\n");
        buffer.close();
        fileTable.delete((StringValue) filePath, buffer);

        return null;

    }

    @Override
    public IStmt deepcopy() {
        return new CloseReadFile(this.expression);
    }

    @Override
    public String toString() {
        return "Close the file" + this.expression;
    }
}
