package com.example.service.primeNumber;

public interface PrimeNumberService {

  enum STATE_PRIME {
    PRIME,
    NOT_PRIME,
    OVERFLOW
  }

  PrimeNumberService.STATE_PRIME IsPrime(long number);

  long findMaxPrimeBeforeOverflow();
}
