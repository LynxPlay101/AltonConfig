package me.lynxplay.altonconfig;

import java.io.File;
import java.util.Arrays;

public class Testing {

    public static void main(String[] args) {
        AltonConfigCore altonConfigCore = new AltonConfigCore();

        TestConfig config = altonConfigCore.load(TestConfig.class, new File(System.getProperty("user.dir"), "config.properties"));
        System.out.println(config.spellNotFound("Lumos"));
        System.out.println("Available ports are: " + Arrays.toString(config.availablePorts()));
    }

}
