package my_map_hashmap_impl;

import java.util.Map;
import java.util.Set;

//from Liang Intro to Java Programming and Data Structures
public interface MyMap<K, V> {

    public void clear();

    public boolean containsKey(K key);

    public boolean containsValue(V value);

    public Set<Entry<K, V>> entrySet();

    public V get(K key);

    public boolean isEmpty();

    public Set<K> keySet();

    public V put (K key, V value);

    public void remove(K key);

    public int size();

    public Set<V> values();

    public static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }
}
