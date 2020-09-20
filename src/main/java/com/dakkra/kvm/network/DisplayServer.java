package com.dakkra.kvm.network;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DisplayServer implements ListChangeListener<Screen> {

    public static final int PORT = 13373;
    private static DisplayServer singleton = null;

    private ObservableList<Screen> displays;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    /**
     * Gets the display server singleton; Creates it if it doesn't exist
     *
     * @param displays Displays to register with this singleton. Only sets on creation
     * @return DisplayServer singleton
     */
    public static DisplayServer getServer(ObservableList<Screen> displays) {
        if (singleton == null) singleton = new DisplayServer(displays);
        return singleton;
    }

    private DisplayServer(ObservableList<Screen> displays) {
        System.out.println("Starting display server");
        //Make sure we watch for changes to the list
        this.displays = displays;
        this.displays.addListener(this);

        System.out.println("Display server starting on port " + PORT);

        try {
            serverSocket = new ServerSocket(PORT);
            //.. do stuff
            serverSocket.close();
            System.out.println("Display server port closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChanged(Change<? extends Screen> c) {
        //TODO handle screen changes here.
    }
}
