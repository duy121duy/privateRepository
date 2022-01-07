package com.example.baitap2_5.cache;

import java.util.*;

public class CacheTTL1<K, V> implements Map<K, V> {
    private HashMap<K, V> hashMapValue;
    private HashMap<K, Long> hashMapTTLwrite;
    private HashMap<K, Long> hashMapTTLread;

    public HashMap<K, V> getHashMapValue() {
        return hashMapValue;
    }

    public void setHashMapValue(HashMap<K, V> hashMapValue) {
        this.hashMapValue = hashMapValue;
    }

    private final long m; //TTL write
    private final long n; //TTL read
    private final int maxSize;

    public CacheTTL1(long m, long n, int maxSize) {
        this.hashMapValue = new HashMap<>();
        this.hashMapTTLwrite = new HashMap<>();
        this.hashMapTTLread = new HashMap<>();
        this.maxSize = maxSize;
        this.m = m;
        this.n = n;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        if (!hashMapValue.containsKey(key)) {
            return null;
        }
        long currentTime = new Date().getTime();
        long TTLw = hashMapTTLwrite.get(key);
        long TTLr = hashMapTTLread.get(key);
        if (currentTime - TTLw > m || currentTime - TTLr > n) {
            hashMapValue.remove(key);
            hashMapTTLwrite.remove(key);
            hashMapTTLread.remove(key);
            return null;
        }
        hashMapTTLread.put((K) key, currentTime);
        return hashMapValue.get(key);
    }

    @Override
    public V put(K key, V value) {
        long currentTime = new Date().getTime();
        if (hashMapValue.size() > maxSize) {
            List<K> keys = new ArrayList<>();
            for (K k : hashMapValue.keySet()) {
                if (currentTime - hashMapTTLwrite.get(k) > m || currentTime - hashMapTTLread.get(k) > n) {
                    keys.add(key);
                }
            }
            for (K k : keys) {
                hashMapValue.remove(k);
                hashMapTTLread.remove(k);
                hashMapTTLwrite.remove(k);
            }
        }

        hashMapTTLread.put(key, currentTime);
        hashMapTTLwrite.put(key, currentTime);
        return hashMapValue.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
