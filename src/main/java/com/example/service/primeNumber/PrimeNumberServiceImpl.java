package com.example.service.primeNumber;

import com.example.service.GCD.GCDService;
import com.example.service.exponentiation.ExponentiationService;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrimeNumberServiceImpl implements PrimeNumberService {

  private final GCDService gcdService;
  private final ExponentiationService exponentiationService;

  @Autowired
  public PrimeNumberServiceImpl(
      GCDService gcdService, ExponentiationService exponentiationService) {
    this.gcdService = gcdService;
    this.exponentiationService = exponentiationService;
  }

  @Override
  public PrimeNumberService.STATE_PRIME IsPrime(long number) {
    if (number == 2) return STATE_PRIME.PRIME;
    if (number % 2 == 0 || number < 2) return STATE_PRIME.NOT_PRIME;

    List<Long> candidates =
        new Random()
            .longs(1, number) // Z n star (1 - (n-1))
            .distinct()
            .limit(Math.min(100, number - 1))
            .filter(a -> gcdService.findGCD(a, number) == 1)
            .boxed()
            .toList();

    for (long candidate : candidates) {
      long expo = exponentiationService.fastExpo(candidate, (number - 1) / 2, number);
      long result = expo % number;

      if (expo == -99) {
        System.out.println(
            "Overflow detected: number = "
                + number
                + ", candidate = "
                + candidate
                + " Expo: "
                + (number - 1) / 2);
        return STATE_PRIME.OVERFLOW;
      }

      if (result != 1 && result != -1 && result != number - 1) {
        System.out.println(
            "Not Prime detected: number = "
                + number
                + ", candidate = "
                + candidate
                + ", result = "
                + result);
        return STATE_PRIME.NOT_PRIME;
      }
    }

    System.out.println("Prime detected: number = " + number);
    return STATE_PRIME.PRIME;
  }

  @Override
  public long findMaxPrimeBeforeOverflow() {
    long maxPrime = 0;
    for (long i = 3; i < Long.MAX_VALUE; i = i + 2) {
      PrimeNumberService.STATE_PRIME state = IsPrime(i);
      if (state == STATE_PRIME.PRIME) maxPrime = i;
      if (state == STATE_PRIME.OVERFLOW) {
        break;
      }
    }
    System.out.println("Max Prime Number = " + maxPrime);
    return maxPrime;
  }
}
