package org.demo.main;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class QuarkusApplicationMain {

    public static void main(String[] args) {
        System.out.println("Application is starting");
        Quarkus.run(args);
    }
}
