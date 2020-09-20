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

    private static void loadLibraries() {
        System.out.println("Loading native libraries");

        try {
            System.loadLibrary("lowLevelInput");

        } catch (UnsatisfiedLinkError e) {
            String path = "dakkralibs/" + UUID.randomUUID().toString() + "t-" + System.currentTimeMillis() + "/";

            InputStream in = DakkraKVM.class.getResourceAsStream("/lowLevelInput.dll");
            File fileOut = new File(System.getProperty("java.io.tmpdir") + "/" + path + "lowLevelInput.dll");

            try {
                fileOut.getParentFile().mkdirs();
                fileOut.createNewFile();
                IOUtils.copy(in, new FileOutputStream(fileOut));
            } catch (Exception fileNotFoundException) {
                System.out.println("Couldn't initialize libraries in a temporary directory");
            }

            System.load(fileOut.toString());
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting DakkraKVM...");

        loadLibraries();
        launch();
        if (displays.size() > 0) {
            System.out.println("Found " + displays.size() + " displays");
            for (Screen s : displays) {
                System.out.println(s.toString());
            }
        }

        DisplayServer displayServer = DisplayServer.getServer(displays);
        InputProcessor inputProcessor = InputProcessor.getInstance();

        System.out.println("Exiting DakkraKVM");
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Detecting displays...");
        displays = Screen.getScreens();
        //..Do UI stuff
        Platform.exit();
    }
}
