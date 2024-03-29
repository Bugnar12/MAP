package model.expression;

import collections.IHeap;
import collections.MyIDictionary;
import exception.InvalidOperator;
import exception.InvalidType;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelationalExp implements Exp{
    private Exp expression1;
    private Exp expression2;
    private String operator;

    public RelationalExp(Exp expression1, Exp expression2, String operator)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }


    @Override
    public Value evaluate(MyIDictionary<String, Value> table, IHeap<Integer, Value> heap) throws Exception {
        Value firstValue = this.expression1.evaluate(table, heap);
        Value secondValue = this.expression2.evaluate(table, heap);

        //first convert from Value(interface) to IntValue(class)
        //then, with another round of parantheses, get the value from the "class" which is the degenerate type int

        int firstIntExp = ((IntValue) firstValue).getVal();
        int secondIntExp = ((IntValue) secondValue).getVal();

        if(firstValue.getType().equals(new IntType()))
        {
            if(secondValue.getType().equals(new IntType()))
            {
                if(this.operator.equals("<"))
                    return new BoolValue(firstIntExp < secondIntExp); //the parameter will be of type BOOLEAN
                if(this.operator.equals("<="))
                    return new BoolValue(firstIntExp <= secondIntExp);
                if(this.operator.equals("=="))
                    return new BoolValue(firstIntExp == secondIntExp);
                if(this.operator.equals("!="))
                    return new BoolValue(firstIntExp != secondIntExp);
                if(this.operator.equals(">"))
                    return new BoolValue(firstIntExp > secondIntExp);
                if(this.operator.equals(">="))
                    return new BoolValue(firstIntExp >= secondIntExp);
                else
                    throw new InvalidOperator("Invalid relational operator!\n");
            }
            else
                throw new InvalidType("Second operand is not an integer!\n");
        }
        else throw new InvalidType("First operand is not an integer!\n");
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        type2 = expression2.typeCheck(typeEnv);

        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
                return new BoolType();
            else
                throw new InvalidType("The second operand should be of type IntType!\n");
        }
        else
            throw new InvalidType("The first operand should be of type IntType!\n");
    }

    @Override
    public String toString() {
        return this.expression1.toString() + this.operator + this.expression2.toString();
    }
}
