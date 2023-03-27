package btvn.model;

import java.io.Serializable;

/**
 *
 * @author tritranmn2
 */
public class Pair<K, V> implements Serializable {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}