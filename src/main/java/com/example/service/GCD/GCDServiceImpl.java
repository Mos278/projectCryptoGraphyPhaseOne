package com.example.service.GCD;

import com.example.service.exponentiation.ExponentiationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GCDServiceImpl implements GCDService {

  // Constructor injection
  @Autowired
  public GCDServiceImpl(ExponentiationService fastExponentiationService) {}

  @Override
  public long findGCD(long first, long second) {
    long a = first;
    long n = second;
    while (n != 0) {
      long temp = a % n;
      a = n;
      n = temp;
    }
    return a;
  }

  @Override
  public long findInverse(
      long base, long mod) { // ค่าใด เมื่อ * กับ base เเล้ว mod กับ modulo เเล้วได้เศษ 1

    if (!isCoprime(base, mod)) {
      System.out.println(base + " and " + mod + " are not coprime");
      return -1;
    }

    long originalMod = mod;

    long x0 = 1, x1 = 0;
    while (mod != 0) {
      long q = base / mod;
      long temp = mod;
      mod = base % mod;
      base = temp;

      temp = x1;
      x1 = x0 - q * x1;
      x0 = temp;
    }
    if (base != 1) return -1; // ไม่มี inverse
    return (x0 + originalMod) % originalMod;
  }

  private boolean isCoprime(long first, long second) {
    return findGCD(first, second) == 1;
  }
}
