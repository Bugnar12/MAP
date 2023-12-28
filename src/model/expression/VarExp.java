package model.expression;

import collections.IHeap;
import collections.MyIDictionary;
import exception.UndefinedVariable;
import model.type.Type;
import model.value.Value;

public class VarExp implements Exp{
    private String id;

    public VarExp(String id)
    {
        this.id = id;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, IHeap<Integer, Value> heap) throws Exception {
        if(!table.isDefined(id))
            throw new UndefinedVariable();
        else
            return table.lookUp(id);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        return typeEnv.lookUp(id);
    }

    @Override
    public String toString() {
        return "VarExp{" +
                "'" + id + '\'' +
                '}';
    }
}
