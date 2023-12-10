package model.type;

import model.value.RefValue;
import model.value.Value;

import java.util.Objects;

public class RefType implements Type{
    private Type inner;
    public RefType(Type inner)
    {
        this.inner = inner;
    }

    public Type getInner()
    {
        return this.inner;
    }
    @Override
    public Value defaultValue() {
        return new RefValue(inner);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof RefType)
            return inner.equals(((RefType) o).
                    getInner());
        else
            return false;
    }

    @Override
    public String toString() { return "Ref(" +inner.toString()+")";}
}
