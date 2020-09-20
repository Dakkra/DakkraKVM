package com.dakkra.kvm.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

    /**
     * Copy all data from stream source to stream dest
     *
     * @param source input stream
     * @param dest   output stream
     * @throws IOException
     */
    public static void copy(InputStream source, OutputStream dest) throws IOException {
        byte[] data = source.readAllBytes();
        System.out.println("Input size: " + data.length);
        dest.write(data);
        dest.flush();
        source.close();
        dest.close();
    }

}
