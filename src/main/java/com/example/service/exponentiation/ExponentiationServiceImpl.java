package com.example.service.exponentiation;

import org.springframework.stereotype.Service;

@Service
public class ExponentiationServiceImpl implements ExponentiationService {

  @Override
  public long bruteForceExpo(long base, long expo) {
    long result = 1L;
    long b = expo;
    while (b > 0) {
      if (result <= 0) return -99; // overflow
      result *= base;
      b--;
    }

    //        System.out.println("base: " + base + " expo: " + expo + " result: " + result);
    return result;
  }

  @Override
  public long fastExpo(long base, long expo) {
    long result = 1L;
    long a = base;
    long b = expo;

    while (b > 0) {
      if ((b & 1) > 0) {
        result = (result * a);
        if (result <= 0) return -99; // overflow
      }
      b >>= 1;
      a = (a * a);
    }

    //        System.out.println("base: " + base + " expo: " + expo + " result: " + result);
    return result;
  }

  @Override
  public long fastExpo(long base, long expo, long mod) {
    long result = 1L;
    long a = base;
    long b = expo;

    while (b > 0) {
      if ((b & 1) > 0) {
        result = (result * a) % mod;
        if (result <= 0) return -99; // overflow
      }
      b >>= 1;
      a = (a * a) % mod;
    }

    //        System.out.println("base: " + base + " expo: " + expo + " result: " + result);
    return result;
  }
}
