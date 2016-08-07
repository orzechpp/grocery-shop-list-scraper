package com.orzechp.sainsburys;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;


public class SainsburysBanner implements Banner {
    public static final String BANNER_FILE = "sainsburys-banner.txt";
    public static final String BANNER_MESSAGE = "Could not find the banner.";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        try {
            List<String> bannerLines = Resources.readLines(Resources.getResource(BANNER_FILE), Charsets.UTF_8);
            bannerLines.forEach(line ->  System.out.println(line));
        } catch (IOException e) {
            System.out.println(BANNER_MESSAGE);
        }
    }
}
