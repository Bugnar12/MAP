package model.type;

import model.value.BoolValue;
import model.value.Value;

public class BoolType implements Type{
    @Override
    public Value defaultValue() {
        return new BoolValue();
    }
    @Override
    public boolean equals(Object another_object)
    {
        if(another_object instanceof BoolType)
            return true;
        else
            return false;
    }

    @Override
    public String toString()
    {
        return "bool";
    }

}
