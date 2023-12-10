package model.expression;

import collections.IHeap;
import collections.MyIDictionary;
import exception.DivisionByZero;
import exception.InvalidOperator;
import exception.InvalidType;
import model.value.Value;

public interface Exp {
    Value evaluate(MyIDictionary<String, Value> table, IHeap<Integer, Value> heap) throws Exception;
}
