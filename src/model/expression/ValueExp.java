package model.expression;
import collections.MyIDictionary;
import exception.InvalidOperator;
import model.value.Value;

public class ValueExp implements Exp{
    private Value expression;
    public ValueExp(Value exp)
    {
        this.expression = exp;
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> table) throws InvalidOperator
    {
        return expression;
    }

    @Override
    public String toString() {
        //return String.format("ValueExpression{%s}", expression.toString());
        return "ValueExpression{" +
                expression +
                '}';
    }
}