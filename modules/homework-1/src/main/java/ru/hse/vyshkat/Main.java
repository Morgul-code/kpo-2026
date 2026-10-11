package ru.hse.vyshkat;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/** Starts a console-only Spring Boot application and closes its context after the demo. */
@SpringBootApplication(proxyBeanMethods = false)
public final class Main {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
                .web(WebApplicationType.NONE)
                .run(args)
                .close();
    }
}
