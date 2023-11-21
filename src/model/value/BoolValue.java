package model.value;

import model.type.BoolType;
import model.type.Type;

public class BoolValue implements Value {
    private final boolean val;
    private static boolean default_boolean = false;

    public BoolValue()
    {
        this.val = BoolValue.default_boolean;
    }
    public BoolValue(boolean v)
    {
        this.val = v;
    }
    public boolean getVal()
    {
        return this.val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof BoolValue))
            return false;
        BoolValue downCastObj = (BoolValue) obj;
        return downCastObj.getVal() == this.val;
    }
}
