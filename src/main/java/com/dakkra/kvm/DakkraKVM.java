/*
DakkraKVM by Christopher Soderquist 2020

Open source cross platform KVM

Project uses MIT license
 */

package com.dakkra.kvm;

import javafx.application.Application;
import javafx.stage.Stage;

public class DakkraKVM extends Application {

    public static void main(String[] args) {
        System.out.println("Starting DakkraKVM...");

        launch();

        System.out.println("Exiting DakkraKVM");
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Launching JavaFX...");
        primaryStage.show();
    }
}
