package org.academiadecodigo.weekendteamwork.javarizer;

import org.academiadecodigo.weekendteamwork.javarizer.util.KeyboardListener;

public class Main {

    /**
     * port number
     */
    public static final int PORT = 30754;


    public static void main(String[] args) {

        new ServerPic();
        new KeyboardListener();
        Server server = new Server(PORT);
        server.start();
    }
}
