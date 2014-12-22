/**
 * A class representing a hashmap
 *
 * @author Bhavika Devnani
 * @version 1.0
 *
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashMap<K, V> implements HashMapInterface<K, V>, Gradable<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMap() {
        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
    }

    @Override
    public V add(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("You passed in null arguments");
        }
        int index = Math.abs(key.hashCode() % table.length);
        V info = null;

        int removed = -1;
        int counter = 0;
        while (table[index] != null && counter < table.length) {
            if (table[index].isRemoved()) {
                if (removed != -1) {
                    removed = index;
                    index++;
                }
            } else {

                if (key.equals(table[index].getKey())) {
                    info = table[index].getValue();
                    table[index].setValue(value);
                    return info;
                } else {
                    index++;
                    index %= table.length;
                }
            }
            counter++;
        }
        if (removed != -1) {
            index = removed;
        }
        table[index] = new MapEntry<>(key, value);
        size++;
        if ((size) / (double) table.length > MAX_LOAD_FACTOR) {
            regrow();
        }
        return info;
    }

    /**
     * Doubles the old table when adding. when exceeded the
     * maximum load factor.
     */

    @SuppressWarnings("unchecked")
    private void regrow() {
        MapEntry<K, V>[] temp = (MapEntry<K, V>[])
            new MapEntry[table.length];
        for (int i = 0; i < table.length; i++) {
            temp[i] = table[i];
        }
        int startingSize = table.length * 2;
        table = (MapEntry<K, V>[]) new MapEntry[startingSize];
        size = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null && !temp[i].isRemoved()) {
                add(temp[i].getKey(), temp[i].getValue());
            }
        }
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("You passed a null key");
        }
        int index = getIndex(key);
        if (index < 0) {
            return null;
        } else {
            table[index].setRemoved(true);
            size--;
            return table[index].getValue();
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Y"
                + "ou passed a null key");
        }
        int index = getIndex(key);
        if (index >= 0) {
            return table[index].getValue();
        } else {
            return null;
        }
    }

/**
 * Gets the index of the given key.
 *
 * @param key of type K: key to be searched for
 * @return index of type int
 */

    private int getIndex(K key) {
        if (key == null) {
            throw new IllegalArgumentException("You passed a null key");
        }
        int index = -1;
        int hashcode = Math.abs(key.hashCode() % table.length);

        int counter = 0;
        while ((table[hashcode] != null && index < 0)
            && (counter < table.length)) {
            if ((!table[hashcode].isRemoved())
                && (table[hashcode].getKey().equals(key))) {
                index = hashcode;
            }
            hashcode++;
            hashcode %= table.length;
            counter++;
        }
        return index;
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key entered is null");
        }
        for (int i = 0; i < table.length; i++) {
            if (((table[i] != null) && (key.equals(table[i].getKey())))
                && (!(table[i].isRemoved()))) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public MapEntry<K, V>[] toArray() {
        return table;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<K>(size);
        for (MapEntry<K, V> entry : table) {
            if ((entry != null) &&
                (!(entry.isRemoved()))) {
                set.add(entry.getKey());
            }
        }
        return set;
    }

    @Override
    public List<V> values() {
        List<V> list = new ArrayList<V>(size);
        for (MapEntry<K, V> entry : table) {
            if ((entry != null) && (!(entry.isRemoved()))) {
                list.add(entry.getValue());
            }
        }
        return list;
    }
}
