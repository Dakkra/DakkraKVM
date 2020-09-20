/*
DakkraKVM by Christopher Soderquist 2020

Open source cross platform KVM

Project uses MIT license
 */

package com.dakkra.kvm;

import com.dakkra.kvm.input.IOUtils;
import com.dakkra.kvm.input.InputProcessor;
import com.dakkra.kvm.network.DisplayServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.util.UUID;

public class DakkraKVM extends Application {

    private static ObservableList<Screen> displays;
    private static boolean keepalive = true;

    /**
     * Attempts to load the native libraries for use with this application
     *
     * @return true if libraries are loaded, false if not or there was an error
     */
    private static boolean loadLibraries() {
        System.out.println("Loading native libraries");

        try {
            System.loadLibrary("lowLevelInput");

        } catch (UnsatisfiedLinkError e) {
            InputStream in;
            String lib_name;

            //Get correct library for operating system
            String operating_system = System.getProperty("os.name").toLowerCase();
            if (operating_system.contains("win")) {
                lib_name = "lowLevelInput.dll";
            } else if (operating_system.contains("mac")) {
                lib_name = "liblowLevelInput.dylib";
            } else {
                lib_name = "libLowLevelInput.so";
            }

            String path = "dakkralibs/" + UUID.randomUUID().toString() + "t-" + System.currentTimeMillis() + "/";
            in = DakkraKVM.class.getResourceAsStream("/" + lib_name);
            File fileOut = new File(System.getProperty("java.io.tmpdir") + "/" + path + lib_name);

            try {
                fileOut.getParentFile().mkdirs();
                fileOut.createNewFile();
                IOUtils.copy(in, new FileOutputStream(fileOut));
            } catch (Exception exception) {
                System.out.println("Couldn't initialize libraries in a temporary directory");
                return false;
            }

            System.load(fileOut.toString());
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Starting DakkraKVM...");

        System.out.println("Pull me in!");

        boolean libraries_loaded = loadLibraries();
        if (libraries_loaded) {
            init_jfx();
            init_services();
        } else {
            System.out.println("Skipping services due to failure to load native libraries");
        }

        while(keepalive){

        }

        System.out.println("Exitting DakkraKVM");
    }

    /**
     * Initialize the JavaFX client. This will consequently update the displays array
     */
    private static void init_jfx() {
        launch();
        if (displays.size() > 0) {
            System.out.println("Found " + displays.size() + " displays");
            for (Screen s : displays) {
                System.out.println(s.toString());
            }
        }
    }

    /**
     * Initialize the singleton services
     */
    private static void init_services() {
        DisplayServer displayServer = DisplayServer.getServer(displays);
        InputProcessor inputProcessor = InputProcessor.getInstance();
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Detecting displays...");
        displays = Screen.getScreens();
        Platform.exit();
    }
}
