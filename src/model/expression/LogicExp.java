package model.expression;

import collections.IHeap;
import collections.MyIDictionary;
import exception.InvalidOperator;
import exception.InvalidType;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class LogicExp implements Exp{
    private Exp expression1;
    private Exp expression2;
    int operation; //1 - AND , 2 - OR

    public LogicExp(Exp expression1, Exp expression2, int operation)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, IHeap<Integer, Value> heap) throws Exception {
        Value value1 = this.expression1.evaluate(table, heap);

        if (value1.getType().equals(new BoolType())) {
            Value value2 = this.expression2.evaluate(table, heap);

            if (value2.getType().equals(new BoolType())) {
                boolean firstBool = ((BoolValue) value1).getVal();
                boolean secondBool = ((BoolValue) value2).getVal();

                if (this.operation == 1)
                {
                    return new BoolValue(firstBool && secondBool);
                }
                if (this.operation == 2)
                {
                    return new BoolValue(firstBool || secondBool);
                } else
                    throw new InvalidOperator();
            } else
                throw new InvalidType("Second operand is not boolean!");
        } else
            throw new InvalidType("First operand is not boolean!");
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        type2 = expression2.typeCheck(typeEnv);

        if(type1.equals(new BoolType()))
        {
            if(type2.equals(new BoolType()))
                return new BoolType();
            else
                throw new InvalidType("The type of the second operand should be BoolType!\n");
        }
        else
            throw new InvalidType("The type of the first operand should be BoolType!\n");
    }

    @Override
    public String toString() {
        return "LogicExp{" +
                "expression1=" + expression1 +
                ", expression2=" + expression2 +
                ", operation=" + operation +
                '}';
    }
}
