package com.orzechp.sainsburys.provider;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class FileWriterProvider {
    public FileWriter provideFileWriter(String filename) throws IOException {
        return new FileWriter(filename);
    }
}