package com.example.service.exponentiation;

import java.math.BigInteger;

public interface ExponentiationService {

  long bruteForceExpo(long base, long expo);

  long fastExpoWithOutMod(long base, long expo);

  long fastExpo(long base, long expo);

  BigInteger binaryFastExpoReturnBigInt(long base, long expo);
}
