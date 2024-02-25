package collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/*Class MyStack implements the ADT Stack from java.util.Stack
* */
public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack()
    {
        stack = new Stack<T>();
    }
    @Override
    public T pop() throws Exception
    {
        if(stack.isEmpty())
            throw new Exception("Stack is empty!");
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /*Here we reverse the stack for getting the elements in the correct order purposes(cred)
    * */
    @Override
    public List<T> reverse_stack() {
        List<T> objects;
        objects = Arrays.asList((T[]) stack.toArray());
        Collections.reverse(objects);
        return objects;
    }

    @Override
    public MyIStack<T> copy() {
        MyStack<T> stack_clone = new MyStack<>(); //raw type
        List<T> reverse_order_stack = reverse_stack();
        for(T elem : reverse_order_stack)
        {
            stack_clone.push(elem);
        }
        return stack_clone;
    }

    //clone() method

    @Override
    public String toString() {
        return "MyStack" + stack;
    }

}
