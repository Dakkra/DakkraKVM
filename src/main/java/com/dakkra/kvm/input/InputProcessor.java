package com.dakkra.kvm.input;

public class InputProcessor {

    private static InputProcessor singleton;

    /**
     * Test method to see if JNI implementation works. Will remove soon
     */
    public native void print();

    public void printJava() {
        System.out.println("This is from java, called by c++");
    }

    /**
     * Gets the instance of this InputProcessor; Creates the instance if none already exists
     *
     * @return InputProcessor instance
     */
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
