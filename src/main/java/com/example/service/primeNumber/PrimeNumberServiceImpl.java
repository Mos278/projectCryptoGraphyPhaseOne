package com.example.service.primeNumber;

import com.example.service.GCD.GCDService;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrimeNumberServiceImpl implements PrimeNumberService {

  private final GCDService gcdService;

  @Autowired
  public PrimeNumberServiceImpl(GCDService gcdService) {
    this.gcdService = gcdService;
  }

  @Override
  public PrimeNumberService.STATE_PRIME IsPrime(
      long number, ExpoAlgorithmMethod expoAlgorithmMethod) {
    if (number == 2) return STATE_PRIME.PRIME;
    if (number % 2 == 0 || number < 2) return STATE_PRIME.NOT_PRIME;

    List<Long> candidates =
        new Random()
            .longs(1, number)
            .distinct()
            .limit(Math.min(100, number - 1))
            .filter(a -> gcdService.findGCD(a, number) == 1)
            .boxed()
            .toList();

    for (long candidate : candidates) {
      long expo = expoAlgorithmMethod.apply(candidate, (number - 1) / 2);
      long result = expo % number;

      if (expo == -1) {
        System.out.println(
            " Overflow detected: number = "
                + number
                + ", candidate = "
                + candidate
                + " Expo: "
                + (number - 1) / 2);
        return STATE_PRIME.OVERFLOW;
      }

      if (result != 1 && result != number - 1) {
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
  public long findMaxPrimeBeforeOverflow(ExpoAlgorithmMethod expoAlgorithmMethod) {
    long maxPrime = 0;
    for (long i = 3; i < Long.MAX_VALUE; i = i + 2) {
      PrimeNumberService.STATE_PRIME state = IsPrime(i, expoAlgorithmMethod);
      if (state == STATE_PRIME.PRIME) maxPrime = i;
      if (state == STATE_PRIME.OVERFLOW) {
        break;
      }
    }
    System.out.println("Max Prime Number = " + maxPrime);
    return maxPrime;
  }
}
