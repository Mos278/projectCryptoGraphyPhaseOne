package com.example;

import com.example.config.AppConfig;
import com.example.service.GCD.GCDService;
import com.example.service.exponentiation.ExponentiationService;
import com.example.service.fileStream.FileInputStreamService;
import com.example.service.primeNumber.PrimeNumberService;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App {
  private final GCDService gcdService;
  private final PrimeNumberService primeNumberService;
  private final ExponentiationService exponentiationService;
  private final FileInputStreamService fileInputStreamService;

  @Autowired
  public App(
      GCDService gcdService,
      PrimeNumberService primeNumberService,
      ExponentiationService exponentiationService,
      FileInputStreamService fileInputStreamService) {
    this.gcdService = gcdService;
    this.primeNumberService = primeNumberService;
    this.exponentiationService = exponentiationService;
    this.fileInputStreamService = fileInputStreamService;
  }

  public void run() {
    try {

      long RandomPrimeNumber = GenPrime(31, "src/main/resources/FB_IMG.jpg");
      long[] result = GenRandomNoWithInverse(RandomPrimeNumber);
      System.out.println("base: " + result[0] + " inverse: " + result[1] + " prime: " + result[2]);
      System.out.println((result[0] * result[1]) % result[2]);

      //
      // System.out.println(primeNumberService.findMaxPrimeBeforeOverflow(exponentiationService::fastExpo));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    App app = context.getBean(App.class);
    app.run();
  }

  private long GenPrime(int bit, String fileName) throws Exception {
    System.out.println("Reading file...");
    long tempResult = fileInputStreamService.randomNBitFromFile(bit, fileName);

    boolean forward = true;
    long max = ((exponentiationService.fastExpoWithOutMod(2, bit) - 1));
    long min = exponentiationService.fastExpoWithOutMod(2, (bit - 1));
    int round = 0;
    boolean firstBackward = true;

    long Result = convertToOdd(tempResult, max);
    System.out.println("result random from file: " + Result);

    while (primeNumberService.isPrimeNumberByLehManTestSetExpo(
            Result, exponentiationService::fastExpo)
        == PrimeNumberService.STATE_PRIME.NOT_PRIME) {
      round++;
      if (forward && Result + 2 < max) {
        Result = Result + 2;
      } else if (Result - 2 > min) {
        forward = false;
        if (firstBackward) {
          Result = tempResult - 2;
          firstBackward = false;
        } else Result = Result - 2;
      } else {
        throw new Exception("Cannot find Prime Number");
      }
      System.out.println("Result from Gen Prime: " + Result + " round: " + round);
    }
    return Result;
  }

  private long[] GenRandomNoWithInverse(long prime) {
    long base = new Random().nextLong(prime - 1);
    while (gcdService.findGCD(base, prime) != 1) {
      base++;
    }
    base = base % prime;

    long inverse = gcdService.findInverse(base, prime);

    return new long[] {base, inverse, prime};
  }

  private long convertToOdd(long tempResult, long max) {
    if (tempResult % 2 == 0) {
      if (tempResult == max) tempResult--;
      else tempResult++;
    }
    return tempResult;
  }
}
//./gradlew spotlessApply
// ./gradlew build    
