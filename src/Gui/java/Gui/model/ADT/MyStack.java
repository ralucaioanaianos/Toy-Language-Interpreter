package Gui.model.ADT;

import Gui.model.exceptions.ADTException;

import java.util.List;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    private Stack<T> stk;

    public MyStack() {
        this.stk = new Stack<T>();
    }

    @Override
    public T pop() throws ADTException {
        if (stk.size() == 0) {
            throw new ADTException("Stack is empty");
        }
        return stk.pop();
    }

    @Override
    public void push(T value) {
        stk.push(value);
    }

    @Override
    public List<T> getValues() {
        return stk.subList(0, stk.size());
    }

    @Override
    public boolean isEmpty() {
        return stk.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("{");
        for (T el: stk) {
            str.append(el).append(" | ");
        }
        str.append("}");
        return str.toString();
    }
}