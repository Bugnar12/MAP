package model.statement;

import collections.MyIDictionary;
import collections.MyIList;
import collections.MyIStack;
import exception.InvalidType;
import model.PrgState;
import model.expression.Exp;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;

public class WhileStmt implements IStmt {
    private Exp expression;
    private IStmt statement;

    public WhileStmt(Exp expression, IStmt statement)
    {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIList<Value> output = state.getOut();

        Value expression_value = expression.evaluate(symbolTable);
        if(!expression_value.getType().equals(new BoolType()))
            throw new InvalidType("The type of the expression should be boolean!");
        else
        {
            if(expression_value.equals(new BoolValue(true)))
            {
                stack.push(this);
                stack.push(statement);
            }
        }
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new WhileStmt(this.expression, this.statement);
    }
}
