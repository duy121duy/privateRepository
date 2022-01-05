package com.example.baitap2_3.controller;

import com.example.baitap2_3.cache.CacheTTL1;
import com.example.baitap2_3.service.FindPrimeNumberService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Controller
public class FindPrimeNumberController {
    LoadingCache<Integer, List<Integer>> integerCache =
            CacheBuilder.newBuilder()
                    .maximumSize(100)                                       // maximum 100 records can be cached
                    .expireAfterAccess(10, TimeUnit.SECONDS)
                    .expireAfterWrite(20, TimeUnit.SECONDS)         // cache will expire after 30 minutes of access
                    .build(new CacheLoader<Integer, List<Integer>>() {             // build the cacheloader

                        @Override
                        public List<Integer> load(Integer key) throws Exception {
                            //make the expensive call
                            System.out.println("No cache");
                            return findPrimeNumberService.findPrimeNumber(key);
                        }
                    });

    private final FindPrimeNumberService findPrimeNumberService;

    public static CacheTTL1<Integer, Integer> cacheTTL = new CacheTTL1<>(10000, 5000, 10000);

    @Autowired
    public FindPrimeNumberController(FindPrimeNumberService findPrimeNumberService) {
        this.findPrimeNumberService = findPrimeNumberService;
    }

    @GetMapping("/prime")
    public ResponseEntity<List<Integer>> getPrimeNumber(@RequestParam(value = "n") int n) {
        long startTime = new Date().getTime();
        List<Integer> primes = findPrimeNumberService.findPrimeNumber(n);
        List<Integer> results = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < primes.size(); i++) {
            Integer prime = cacheTTL.get(i);
            if (prime != null) {
                results.add(prime);
            } else {
                prime = findPrimeNumberService.findPrimeNumber(n).get(i);
                cacheTTL.put(i, prime);
                results.add(prime);
            }
        }
        long endTime = new Date().getTime();
        System.out.println(endTime - startTime);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/primeCache")
    public ResponseEntity<List<Integer>> getPrimeNumberCache(@RequestParam(value = "n") int n) {
        try {
            return new ResponseEntity<>(integerCache.get(n), HttpStatus.OK);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
