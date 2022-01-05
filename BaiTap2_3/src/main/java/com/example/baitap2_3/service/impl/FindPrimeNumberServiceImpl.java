package com.example.baitap2_3.service.impl;

import com.example.baitap2_3.service.FindPrimeNumberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindPrimeNumberServiceImpl implements FindPrimeNumberService {
    @Override
    public List<Integer> findPrimeNumber(int n) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (checkPrimeNumber(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    public boolean checkPrimeNumber(int number) {
        if (number == 1) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        boolean check = true;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                check = false;
                break;
            }
        }
        return check;
    }
}
