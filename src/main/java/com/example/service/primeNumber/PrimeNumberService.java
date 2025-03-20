package com.example.service.primeNumber;

public interface PrimeNumberService {

  enum STATE_PRIME {
    PRIME,
    NOT_PRIME,
    OVERFLOW
  }

  PrimeNumberService.STATE_PRIME IsPrime(long number, ExpoAlgorithmMethod expoAlgorithmMethod);

  long findMaxPrimeBeforeOverflow(ExpoAlgorithmMethod expoAlgorithmMethod);

  interface ExpoAlgorithmMethod {
    Long apply(Long base, Long expo);
  }
}
