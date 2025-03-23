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
  public long findInverse(long a, long p) {
    // ค่าใด เมื่อ * กับ a เเล้ว p กับ modulo เเล้วได้เศษ 1

    if (!isCoprime(a, p)) {
      System.out.println(a + " and " + p + " are not coprime");
      return -1;
    }

    long originalP = p;

    long x0 = 1, x1 = 0;
    while (p != 0) {
      long q = a / p;
      long temp = p;
      p = a % p;
      a = temp;

      temp = x1;
      x1 = x0 - q * x1;
      x0 = temp;
    }
    if (a != 1) return -1;
    return (x0 + originalP) % originalP;
  }

  private boolean isCoprime(long first, long second) {
    return findGCD(first, second) == 1;
  }
}
