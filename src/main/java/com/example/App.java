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

  private final FileInputStreamService fileInputStreamService;
  private final ExponentiationService exponentiationService;
  private final PrimeNumberService primeNumberService;
  private final GCDService gcdService;

  @Autowired
  public App(
      FileInputStreamService fileInputStreamService,
      ExponentiationService exponentiationService,
      PrimeNumberService primeNumberService,
      GCDService gcdService) {
    this.fileInputStreamService = fileInputStreamService;
    this.exponentiationService = exponentiationService;
    this.primeNumberService = primeNumberService;
    this.gcdService = gcdService;
  }

  public void run() {
    try {
      long RandomPrimeNumber = GenPrime(32, "src/main/resources/FB_IMG.jpg");
      long[] result = GenRandomNoWithInverse(RandomPrimeNumber);
      System.out.println("base: " + result[0] + " inverse: " + result[1] + " prime: " + result[2]);
      System.out.println((result[0] * result[1]) % result[2]);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    App app = context.getBean(App.class);
    app.run();
  }

  public long GenPrime(int bit, String fileName) throws Exception {
    System.out.println("Reading file...");
    long tempResult = fileInputStreamService.randomNBitFromFile(bit, fileName);

    boolean forward = true;
    long max = ((exponentiationService.fastExpo(2, bit) - 1));
    long min = exponentiationService.fastExpo(2, (bit - 1));
    int round = 0;
    boolean firstBackward = true;

    long resultOdd = convertToOdd(tempResult, max);
    System.out.println("result random from file: " + resultOdd);
    long tempResultOdd = resultOdd;
    while (primeNumberService.IsPrime(resultOdd) == PrimeNumberService.STATE_PRIME.NOT_PRIME) {
      round++;
      if (forward && resultOdd + 2 < max) {
        resultOdd = resultOdd + 2;
      } else if (resultOdd - 2 > min) {
        forward = false;
        if (firstBackward) {
          resultOdd = tempResultOdd - 2;
          firstBackward = false;
        } else resultOdd = resultOdd - 2;
      } else {
        throw new Exception("Cannot find Prime Number");
      }
      System.out.println("Result from Gen Prime: " + resultOdd + " round: " + round);
    }
    return resultOdd;
  }

  public long[] GenRandomNoWithInverse(long n) {
    long e = new Random().nextLong(n - 1) + 1; // 1 - (n - 1)

    while (gcdService.findGCD(e, n) != 1) {
      e++;
      if (e >= n) e = 1;
    }
    // Select e = [1 - (n - 1)] && gcd(e,n) == 1
    long inverse = gcdService.findInverse(e, n);

    return new long[] {e, inverse, n};
  }

  private long convertToOdd(long tempResult, long max) {
    if (tempResult % 2 == 0) {
      if (tempResult == max) tempResult--;
      else tempResult++;
    }
    return tempResult;
  }
}
// ./gradlew spotlessApply
// ./gradlew build
