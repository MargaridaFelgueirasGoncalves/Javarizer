package org.academiadecodigo.weekendteamwork.javarizer;

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
    private int connections;
    private int maxConnections;

    private ServerSocket serverSocket;
    private ExecutorService playerService;
    private ExecutorService quizService;
    private final List<Player> playersList;

    private QuizRound quizRound;

    /**
     * constructor
     */
    public Server(int port) {

        playerService = Executors.newCachedThreadPool();
        quizService = Executors.newCachedThreadPool();
        playersList = Collections.synchronizedList(new LinkedList<>());

        connections = 0;
        maxConnections = 10;

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

                Player player = new Player(playerSocket, this);

                // add player to the list
                playersList.add(player);

                limitPlayers(player);

                player.setUsername(player.askUsername());

                if (connections < maxConnections - 1) player.getOut().println("Let's wait for the other players...");

                // start thread pool
                playerService.submit(player);

                connections++;

            } catch (IOException e) {
                System.err.println("Error establishing connection: " + e.getMessage());
            }
        }

        checkPlayers(playersList);

        startDrawing();

        startQuiz();

    }

    public void startQuiz() {

        for (Player player : playersList) {
            quizService.submit(new QuizRound(player, this));

        }
    }

    public void startDrawing() {

        String mates;

        if (maxConnections == 1) {
            mates = "|  So I see you have no friends... Just like me...   |\n";
        } else{
            mates = "|              Quiz will start in 3 seconds...       |\n";
        }

        broadcast("\n\n                        .-\"\"\"-.\n" +
                "                       / .===. \\\n" +
                "                       \\/ 6 6 \\/\n" +
                "                       ( \\___/ )\n" +
                "  _________________ooo__\\_____/_____________________\n" +
                " /                                                  \\\n" +
                mates +
                " \\______________________________ooo_________________/\n" +
                "                       |  |  |\n" +
                "                       |_ | _|\n" +
                "                       |  |  |\n" +
                "                       |__|__|\n" +
                "                       /-'Y'-\\\n" +
                "                      (__/ \\__)\n\n");

        try {
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to get players username and check for null usernames
     * @param playersList
     */
    public void checkPlayers(List<Player> playersList){

        for (Player player: playersList){
            if (player.getUsername() == null){
                checkPlayers(playersList);
            }
        }
    }

    /**
     * broadcast a string to all players
     * @param string
     */
    public void broadcast (String string) {

        for (Player player : playersList) {
            PrintStream writer = new PrintStream(player.getOut());
            writer.println(string);
        }
    }

    /**
     * method to limit number of players in order to start with a fixed number
     * @param player
     */
    public void limitPlayers (Player player) {

        if (connections== 0) {
            maxConnections=player.limitPlayers();
        }
    }

    // getter
    public List<Player> getPlayersList() {
        return playersList;
    }
}
