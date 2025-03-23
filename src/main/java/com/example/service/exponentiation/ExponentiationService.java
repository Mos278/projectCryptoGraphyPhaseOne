package com.example.service.exponentiation;

public interface ExponentiationService {

  long bruteForceExpo(long base, long expo);

  long fastExpo(long base, long expo);

  long fastExpo(long base, long expo, long mod);
}
