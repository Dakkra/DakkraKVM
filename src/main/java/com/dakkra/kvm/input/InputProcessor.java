package com.dakkra.kvm.input;

public class InputProcessor {

    private static InputProcessor singleton;

    public native void print();

    public static InputProcessor getInstance() {
        if (singleton == null) singleton = new InputProcessor();
        return singleton;
    }

    private InputProcessor() {
        System.out.println("Created input processor");
        System.out.println("Calling native method");
        print();
    }
}
