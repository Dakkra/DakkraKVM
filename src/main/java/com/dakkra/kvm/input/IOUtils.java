package com.dakkra.kvm.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

    public static void copy(InputStream source, OutputStream dest) {
        try {
            byte[] data = source.readAllBytes();
            System.out.println("Input size: " + data.length);
            dest.write(data);
            dest.flush();
            source.close();
            dest.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
