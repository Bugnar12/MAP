package model.expression;

import collections.MyIDictionary;
import exception.UndefinedVariable;
import model.value.Value;

public class VarExp implements Exp{
    private String id;

    public VarExp(String id)
    {
        this.id = id;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table) throws Exception {
        if(!table.isDefined(id))
            throw new UndefinedVariable();
        else
            return table.lookUp(id);
    }

    @Override
    public String toString() {
        return "VarExp{" +
                "'" + id + '\'' +
                '}';
    }
}
