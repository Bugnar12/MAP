package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value{
    private final int val;
    public static final int default_int = 0;

    public IntValue()
    {
        this.val = IntValue.default_int;
    }

    public IntValue(int v){
        this.val = v;
    }

    public int getVal() {
        return this.val;
    }

    @Override
    public Type getType()
    {
        return new IntType();
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof IntValue))
            return false;
        IntValue downCastObj = (IntValue) obj;
        //return this.val == downCastObj.val;
        return downCastObj.getVal() == this.val;
    }
}
