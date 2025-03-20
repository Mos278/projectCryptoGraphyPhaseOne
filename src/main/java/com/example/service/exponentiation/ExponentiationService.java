package com.example.service.exponentiation;

import java.math.BigInteger;

public interface ExponentiationService {

  long bruteForceExpo(long base, long expo);

  long fastExpo(long base, long expo);

  long fastExpo(long base, long expo, long mod);

  BigInteger binaryFastExpoReturnBigInt(long base, long expo);
}
