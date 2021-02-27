package org.academiadecodigo.weekendteamwork.javarizer;

public class Main {

    /**
     * port number
     */
    public static final int PORT = 30754;


    public static void main(String[] args) {

        new ServerPic();
        Server server = new Server(PORT);
        server.start();
    }
}
