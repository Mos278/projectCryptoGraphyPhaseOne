package com.example.service.generator;

import com.example.service.exponentiation.ExponentiationService;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneratorServiceImpl implements GeneratorService {

  private final ExponentiationService exponentiationService;

  @Autowired
  public GeneratorServiceImpl(ExponentiationService exponentiationService) {
    this.exponentiationService = exponentiationService;
  }

  @Override
  public long findGenerator(long prime) {
    Random rand = new Random();
    long g = rand.nextLong(prime - 3) + 2;
    if (!CheckGenerator(g, prime)) {
      g = prime - g;
    }
    return g;
  }

  private boolean CheckGenerator(long g, long p) {
    if (exponentiationService.fastExpo(g, (p - 1) / 2) == 1) {
      return false;
    }
    return true;
  }
}
