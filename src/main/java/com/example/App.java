package com.example;

import com.example.config.AppConfig;
import com.example.controller.phaseOne.PhaseOneController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App {

  private final PhaseOneController phaseOneController;

  @Autowired
  public App(PhaseOneController phaseOneController) {
    this.phaseOneController = phaseOneController;
  }

  public void run() {
    phaseOneController.PresentPhase();
  }

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    App app = context.getBean(App.class);
    app.run();
  }
}
// ./gradlew spotlessApply
// ./gradlew build
