package model.type;

import model.value.IntValue;
import model.value.Value;

public class IntType implements Type{
    @Override
    public Value defaultValue() {
        return new IntValue();
    }

    public boolean equals(Object other_object)
    {
        if(other_object instanceof IntType)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {return "int";}
}
