package model.statement;

import collections.MyIDictionary;
import collections.MyIStack;
import exception.InvalidType;
import model.PrgState;
import model.expression.Exp;
import model.expression.VarExp;
import model.type.BoolType;
import model.type.Type;

public class ConditionalAssignment implements IStmt{
    private String variable;
    private Exp expression1;
    private Exp expression2;
    private Exp expression3;

    public ConditionalAssignment(String variable, Exp expression1, Exp expression2, Exp expression3) {
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIStack<IStmt> executionStack = state.getExeStack();
        //we check the boolean value of expression1 and decide if what result we conclude to(expression2 OR expression3)
        IfStmt result = new IfStmt(expression1, new AssignStmt(variable, expression2), new AssignStmt(variable, expression3));
        executionStack.push(result);
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type typeVariable = new VarExp(variable).typeCheck(typeEnv);
        Type typeExpression1 = expression1.typeCheck(typeEnv);
        Type typeExpression2 = expression2.typeCheck(typeEnv);
        Type typeExpression3 = expression3.typeCheck(typeEnv);
        if(typeExpression1.equals(new BoolType()) && typeExpression2.equals(typeVariable) && typeExpression3.equals(typeVariable))
            return typeEnv;
        else
            throw new InvalidType("ConditionalAssignment : different types! typeCheck\n");
    }

    @Override
    public String toString() {
        return "ConditionalAssignment{" +
                "v='" + variable + '\'' +
                ", exp1=" + expression1 +
                ", exp2=" + expression2 +
                ", exp3=" + expression3 +
                '}';
    }
}

