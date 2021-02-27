package org.academiadecodigo.weekendteamwork.javarizer.server;

import org.academiadecodigo.weekendteamwork.javarizer.player.Player;
import java.io.IOException;
import java.io.PrintStream;
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
    private int maxConnections;
    private QuizRound quizRound;

    /**
     * constructor
     */
    public Server(int port) {

        service = Executors.newCachedThreadPool();
        playersList = Collections.synchronizedList(new LinkedList<>());

        connections = 0;
        maxConnections = 5;

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

        while (connections != maxConnections) {

            try {
                System.out.println("Waiting for connection...");
                Socket playerSocket = serverSocket.accept();
                System.out.println("New client connection" + playerSocket);


                ConnectionHandler connectionHandler = new ConnectionHandler(playerSocket, this);

                Player player = new Player(connectionHandler);

                // add player to the list
                playersList.add(player);

                player.setUsername(connectionHandler.askUsername());

                // start thread pool
                service.submit(connectionHandler);

                connections++;

            } catch (IOException e) {
                System.err.println("Error establishing connection: " + e.getMessage());

            }
        }

        checkPlayers(playersList);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startQuiz(quizRound);

    }

    public void startQuiz(QuizRound round) {

        for (Player player : playersList) {
            round = new QuizRound(player.getConnectionHandler().getPrompt(), this);

            service.submit(round);

            PrintStream writer = new PrintStream(player.getConnectionHandler().getOut());

        }
    }


    public void checkPlayers(List<Player> playersList){

        for (Player player: playersList){
            if (player.getUsername() == null){
                checkPlayers(playersList);

            }
        }
    }


    public void broadcast (String string) {

        for (Player player : playersList) {
            PrintStream writter = new PrintStream(player.getConnectionHandler().getOut());
            writter.println(string);

        }
    }

    // getter
    public int getConnections() {
        return connections;
    }

    // setter
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

}
