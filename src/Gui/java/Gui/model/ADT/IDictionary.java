package Gui.model.ADT;

import Gui.model.exceptions.ADTException;

import java.util.Map;

public interface IDictionary<K, V> {
    public V lookup(K key);
    public void update(K key, V value);
    public void remove(K key) throws ADTException;

    boolean isDefined(K id);

    void add(K name, V intValue) throws ADTException;
    public Map<K, V> getContent();
    IDictionary<K,V> clone();
}