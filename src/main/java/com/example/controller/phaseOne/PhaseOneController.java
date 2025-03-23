package com.example.controller.phaseOne;

public interface PhaseOneController {

  void PresentPhase();

  long GenPrime(int bit, String fileName) throws Exception;

  long[] GenRandomNoWithInverse(long prime);
}
