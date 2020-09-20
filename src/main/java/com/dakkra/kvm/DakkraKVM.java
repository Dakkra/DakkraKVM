/*
DakkraKVM by Christopher Soderquist 2020

Open source cross platform KVM

Project uses MIT license
 */

package com.dakkra.kvm;

import com.dakkra.kvm.network.DisplayServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DakkraKVM extends Application {

    private static ObservableList<Screen> displays;

    public static void main(String[] args) {
        System.out.println("Starting DakkraKVM...");

        launch();
        if (displays.size() > 0) {
            System.out.println("Found " + displays.size() + " displays");
            for (Screen s : displays) {
                System.out.println(s.toString());
            }
        }

        DisplayServer displayServer = DisplayServer.getServer(displays);

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
