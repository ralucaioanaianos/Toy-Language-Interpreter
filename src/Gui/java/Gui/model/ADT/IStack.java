package Gui.model.ADT;

import Gui.model.exceptions.ADTException;

import java.util.List;

public interface IStack<T> {
    public T pop() throws ADTException;
    public void push(T value);
    List<T> getValues();
    boolean isEmpty();
}