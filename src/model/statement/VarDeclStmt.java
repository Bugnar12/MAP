package model.statement;

import collections.MyIDictionary;
import collections.MyIList;
import collections.MyIStack;
import exception.ExistingVariable;
import exception.InvalidType;
import model.PrgState;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class VarDeclStmt implements IStmt{
    private String name;
    private Type type;
    public VarDeclStmt(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIList<Value> output = state.getOut();


        if(symbolTable.isDefined(name))
            throw new ExistingVariable("Variable" + name + "already defined");
        else
        {
//            Value new_value;
//            if (type.equals(new IntType())) {
//                int val = 0;
//                new_value = new IntValue(val);
//            } else if (type.equals(new BoolType())) {
//                boolean val = false;
//                new_value = new BoolValue(val);
//            } else
//                throw new InvalidType();
//            symbolTable.put(this.name, new_value);

            symbolTable.put(name, type.defaultValue());
        }

        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new VarDeclStmt(name, type);
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
