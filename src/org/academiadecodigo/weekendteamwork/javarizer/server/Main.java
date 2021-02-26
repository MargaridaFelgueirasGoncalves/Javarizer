package org.academiadecodigo.weekendteamwork.javarizer.server;

public class Main {

    /**
     * port number
     */
    public static final int PORT = 30754;


    public static void main(String[] args) {

        Server server = new Server(PORT);
        server.start();
    }
}
