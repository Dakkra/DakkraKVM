package com.dakkra.kvm.input;

public class InputProcessor {

    private static InputProcessor singleton;

    public static InputProcessor getInstance() {
        if (singleton == null) singleton = new InputProcessor();
        return singleton;
    }
}
