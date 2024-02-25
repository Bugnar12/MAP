package model.statement;

import collections.IHeap;
import collections.MyIDictionary;
import collections.MyIStack;
import exception.InvalidType;
import exception.UndefinedVariable;
import model.PrgState;
import model.expression.Exp;
import model.expression.VarExp;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;

public class ReadFile implements IStmt{
    private Exp expression;
    private String variableName;

    public ReadFile(Exp expression, String variableName)
    {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        //MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symbolTable= state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if(!symbolTable.isDefined(variableName))
            throw new UndefinedVariable("Variable not declared in symbol table!\n");

        Value valueOfVariable = symbolTable.lookUp(variableName);
        Type typeOfVariable = valueOfVariable.getType();

        if(!typeOfVariable.equals(new IntType()))
            throw new InvalidType("The type of the variable should be int!");

        Value filePathValue = expression.evaluate(symbolTable, heap);

        if(!filePathValue.getType().equals(new StringType()))
            throw new InvalidType("The type of the filePath should be StringValue!\n");

        if(!fileTable.isDefined((StringValue) filePathValue))
            throw new UndefinedVariable("Variable not declared in the file table!\n");

        try {
            BufferedReader buffer = fileTable.lookUp((StringValue) filePathValue);
            String line = buffer.readLine();
            if (line == null) {
                Value newValue = new IntValue();
                symbolTable.update(variableName, newValue);
            } else {
                Value newValue = new IntValue(Integer.parseInt(line));
                symbolTable.update(variableName, newValue);
            }
        }
            catch (IOException e)
            {
                throw new IOException("Error when reading from file!\n");
            }
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new ReadFile(expression, variableName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type typeVariable = typeEnv.lookUp(variableName);
        Type typeExpression = expression.typeCheck(typeEnv);

        if(typeVariable.equals(new IntType())) {
            if (typeExpression.equals(new StringType())) {
                return typeEnv;
            }
            else
                throw new InvalidType("ReadFile : type of expression must be StringType!\n");
        }
        else
            throw new InvalidType("ReadFile : type of the variable must be IntType!\n");
    }

    @Override
    public String toString() {
        return "ReadFile(" +
                expression +
                ")\n";

    }
}
