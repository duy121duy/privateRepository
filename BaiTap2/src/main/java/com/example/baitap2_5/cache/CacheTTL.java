package com.example.baitap2_5.cache;


import com.example.baitap2_5.entity.WrappedValue;
import com.example.baitap2_5.service.FindPrimeNumberService;
import com.example.baitap2_5.service.impl.FindPrimeNumberServiceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CacheTTL<K, V> extends HashMap<K, V> {
    private final FindPrimeNumberService findPrimeNumberService = new FindPrimeNumberServiceImpl();
    private static int m; // TTL write
    private static int n; // TTL read

    public CacheTTL(int m, int n) {
        CacheTTL.m = m;
        CacheTTL.n = n;
    }

    @Override
    public V get(Object key) {
        long currentTime = new Date().getTime();
        WrappedValue existedKey = (WrappedValue) super.get(key);
        if (existedKey != null) {
            if (currentTime - existedKey.getTTLread() > n || currentTime - existedKey.getTTLwrite() > m) {
                remove(key);
                System.out.println("Get - remove cache");
                return null;
            } else {
                System.out.println("Get - get cache");
                existedKey.setTTLread(currentTime);
            }
        }
        return (V) existedKey;
    }

    @Override
    public V put(K key, V value) {
        long currentTime = new Date().getTime();
        System.out.println("PUT - push new key");
        return super.put(key, (V) new WrappedValue(((WrappedValue) value).getValue(), currentTime, currentTime));
    }

    Map<K, V> getMap() {
        long currentTime = new Date().getTime();
        Map<K, V> map = new HashMap<>();
        for (K key : super.keySet()) {
            WrappedValue wrappedValue = (WrappedValue) super.get(key);
            if (currentTime - wrappedValue.getTTLread() < n && currentTime - wrappedValue.getTTLwrite() > m) {
                map.put(key, (V) wrappedValue);
                System.out.println(key + " " + wrappedValue.toString());
            } else {
                remove(key);
            }
        }
        return map;
    }

}
