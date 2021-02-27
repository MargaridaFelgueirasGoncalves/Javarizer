package org.academiadecodigo.weekendteamwork.javarizer.server;

import org.academiadecodigo.weekendteamwork.javarizer.player.Player;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    /**
     * fields
     */
    private ServerSocket serverSocket;
    private final ExecutorService service;
    private final List<Player> playersList;
    private int connections;

    /**
     * constructor
     */
    public Server(int port) {

        service = Executors.newFixedThreadPool(5);
        playersList = Collections.synchronizedList(new LinkedList<>());

        connections = 0;

        try {
            serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method called in main
     */
    public void start() {
        waitConnection();
    }

    /**
     * method to loop through connections
     * add each connection to the players list
     * start thread pool
     */
    private void waitConnection() {

        while (connections < 5) {

            try {
                System.out.println("Waiting for connection...");
                Socket playerSocket = serverSocket.accept();
                connections++;
                System.out.println("New client connection" + playerSocket);

                ConnectionHandler connectionHandler = new ConnectionHandler(playerSocket, this);

                Player player = new Player(connectionHandler);
                // add player to the list
                playersList.add(player);

                // start thread pool
                service.submit(connectionHandler);


            } catch (IOException e) {
                System.err.println("Error establishing connection: " + e.getMessage());
            }
        }
    }

    public void broadcast() {

        for (Player player : playersList) {

            PrintStream writter = new PrintStream(player.getConnectionHandler().getOut());
            writter.println("Hello World");

        }


    }

}
