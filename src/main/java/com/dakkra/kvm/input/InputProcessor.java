package com.dakkra.kvm.input;

public class InputProcessor {

    private static InputProcessor singleton;

    /**
     * Registers the InputProcessor with the native keyboard and mouse input
     */
    public native void register_key_listener();

    public void native_key_down(int keycode) {
        System.out.println("Key Down: " + keycode);
    }

    public void native_key_up(int keycode) {
        System.out.println("Key Up: " + keycode);
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
        System.out.println("Registering native input handler");
    }
}
