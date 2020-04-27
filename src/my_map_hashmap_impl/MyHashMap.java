package my_map_hashmap_impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MyHashMap<K, V> implements MyMap<K, V> {

    private final static int DEFAULT_INITIAL_CAPACITY = 4;
    private final static int MAXIMUM_CAPACITY = 1 << 30;

    private int capacity;

    private final static float DEFAULT_MAX_LOAD_FACTOR = 0.75f;

    private float loadFactorThreshold;

    private int size;

    //an array of linkedlists for separate chaining hash to index collision resolution
    //every linked list is a bucket
    LinkedList<Entry<K, V>>[] table;

    //3 constructors
    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, float loadFactorThreshold) {
        if (initialCapacity > MAXIMUM_CAPACITY) {
            this.capacity = MAXIMUM_CAPACITY;
        } else
            this.capacity = trimToPowerOf2(initialCapacity);

        this.loadFactorThreshold = loadFactorThreshold;
        table = new LinkedList[capacity];
    }

    @Override
    public void clear() {
        size = 0;
        removeEntries();
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LinkedList<Entry<K, V>> bucket = table[i];
                for (Entry<K, V> entry : bucket) {
                    if (entry.getValue().equals(value))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                //if the bucket (table[i]) is not null, add all entries from it to the set
                set.addAll(table[i]);
            }
        }
        return set;
    }

    @Override
    public V get(K key) {
        int bucketIndex = hash(key.hashCode());
        if (table[bucketIndex] != null) {
            LinkedList<Entry<K, V>> bucket = table[bucketIndex];
            for (Entry<K, V> entry : bucket) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LinkedList<Entry<K, V>> bucket = table[i];
                for (Entry<K, V> entry : bucket) {
                    set.add(entry.getKey());
                }
            }
        }
        return set;
    }

    @Override
    public V put(K key, V value) {

        //if the key is already in the map, replace its value
        if (get(key) != null) {
            int bucketIndex = hash(key.hashCode());
            LinkedList<Entry<K, V>> bucket = table[bucketIndex];
            for (Entry<K, V> entry : bucket) {
                if (entry.getKey()
                        .equals(key)) {
                    V oldValue = entry.getValue();
                    entry.value = value;
                    return oldValue;
                }
            }
        }

        //make sure there's room in the table for the new entry
        if (size >= capacity * loadFactorThreshold) {
            if (capacity == MAXIMUM_CAPACITY) {
                rehash();
            }
        }

        int bucketIndex = hash(key.hashCode());

        //create a new bucket for a if the element to which the k, v pair is to be added is null
        if (table[bucketIndex] == null) {
            table[bucketIndex] = new LinkedList<>();
        }

        table[bucketIndex].add(new Entry(key, value));
        size++;

        return value;
    }

    @Override
    public void remove(K key) {
        int bucketIndex = hash(key.hashCode());

        //remove the first entry matching the key from a bucket
        if (table[bucketIndex] != null) {
            LinkedList<Entry<K, V>> bucket = table[bucketIndex];
            for (Entry<K, V> entry : bucket) {
                if (entry.getKey().equals(key)) {
                    bucket.remove(entry);
                    size--;
                    break;
                }
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<V> values() {
        Set<V> set = new HashSet<>();

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LinkedList<Entry<K, V>> bucket = table[i];
                for (Entry<K, V> entry : bucket) {
                    set.add(entry.getValue());
                }
            }
        }

        return set;
    }

    private int hash(int hashCode) {
        return supplementalHash(hashCode) & (capacity - 1);
    }

    private int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private int trimToPowerOf2(int initialCapacity) {
        int capacity = 1;
        while (capacity < initialCapacity) {
            //raising to consecutive powers of 2 (same as capacity *= 2 but book says more efficient
            //probs because straight on binary values)
            capacity <<= 1;
        }
        return capacity;
    }

    private void removeEntries() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                table[i].clear();
            }
        }
    }

    private void rehash() {
        Set<Entry<K, V>> set = entrySet();
        capacity <<= 1; // double the capacity
        table = new LinkedList[capacity];
        size = 0;

        for (Entry<K, V> entry : set) {
            put(entry.getKey(), entry.getValue()); //store to new table
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        for (int i = 0; i < capacity; i++)
            if (table[i] != null && table[i].size() > 0)
                for (Entry<K, V> entry : table[i])
                    builder.append(entry);

        builder.append("]");
        return builder.toString();
    }
}
