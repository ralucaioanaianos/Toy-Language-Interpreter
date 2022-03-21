package Gui.model.ADT;

import Gui.model.exceptions.ADTException;

import java.util.List;

public interface IList<T> {
    void add(T item);
    void remove(T item) throws ADTException;
    int size();
    T get(int index) throws ADTException;
    List getContenbt();

}