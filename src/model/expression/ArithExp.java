package model.expression;

import collections.MyIDictionary;
import exception.DivisionByZero;
import exception.InvalidOperator;
import exception.InvalidType;
import model.type.IntType;
import model.value.IntValue;
import model.value.Value;

public class ArithExp implements Exp {
    private final Exp exp1;
    private final Exp exp2;
    private final int operation; //1-plus, 2-minus, 3-multiplication, 4-division

    public ArithExp(Exp exp1, Exp exp2, int operation) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table) throws Exception
    {
        Value value1, value2;
        value1 = exp1.evaluate(table);
        if (value1.getType().equals(new IntType())) //if we have an integer
        {
            value2 = exp2.evaluate(table);
            if (value2.getType().equals(new IntType())) {
                int firstInteger = ((IntValue) value1).getVal();
                int secondInteger = ((IntValue) value2).getVal();
                if (operation == 1) return new IntValue(firstInteger + secondInteger );
                if (operation == 2) return new IntValue(firstInteger - secondInteger);
                if (operation == 3) return new IntValue(firstInteger * secondInteger);
                if (operation == 4) {
                    if (secondInteger == 0)
                        throw new DivisionByZero();
                    else return new IntValue(firstInteger / secondInteger);
                } else throw new InvalidOperator("Invalid operation");
            } else
                throw new InvalidType("Second operand is not integer");
        } else
            throw new InvalidType("First operator is not an integer");
    }

    @Override
    //basic toString method
    public String toString(){
           String op = "";
            if(operation == 1)
                op = "+";
            if(operation == 2)
                op = "-";
            if(operation == 3)
                op = "*";
            if(operation == 4)
                op = "/";
            return exp1.toString() + " " + op + " " + exp2.toString();
    }
}