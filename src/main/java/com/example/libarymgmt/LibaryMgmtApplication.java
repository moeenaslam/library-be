package com.example.libarymgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@SpringBootApplication
public class LibaryMgmtApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(LibaryMgmtApplication.class).profiles("dev","prod").run(args);
  }

}


@Component
class MyRunner implements CommandLineRunner {

  @Autowired
  private Environment environment;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Active profiles: " +
        Arrays.toString(environment.getActiveProfiles()));
  }
}
