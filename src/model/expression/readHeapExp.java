package model.expression;

import collections.IHeap;
import collections.MyIDictionary;
import exception.InvalidType;
import exception.UndefinedVariable;
import model.value.RefValue;
import model.value.Value;

public class readHeapExp implements Exp{
    private Exp expression;
    public readHeapExp(Exp expression)
    {
        this.expression = expression;
    }

    //here we just extract the Value(IntValue, RefValue)
    //from the HeapTable using the address
    @Override
    public Value evaluate(MyIDictionary<String, Value> table, IHeap<Integer, Value> heap) throws Exception {
        Value valueExpression = expression.evaluate(table, heap);
        if(!(valueExpression instanceof RefValue))
            throw new InvalidType("The value should be of type Ref!");

        int address = ((RefValue) valueExpression).getAddress();
        if(!(heap.containsKey(address)))
            throw new UndefinedVariable(" No declaration in Heap Table!");

        //returns the specific Value from the heap table using the address
        return heap.get(address);
    }

    @Override
    public String toString() {
        return "readHeapExp{ "+ expression.toString() +
                " }";
    }
}
