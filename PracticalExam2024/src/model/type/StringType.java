package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type{

    @Override
    public Value defaultValue() {
        return new StringValue();
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof StringType)
            return true;
        return false;
    }
}
