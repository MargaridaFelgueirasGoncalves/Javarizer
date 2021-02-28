package org.academiadecodigo.weekendteamwork.javarizer;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.Socket;

public class Player implements Runnable {

    /**
     * fields
     */
    private String username;
    private int score;
    private boolean finished = false;

    private Socket playerSocket;
    private final Server server;

    private Prompt prompt;
    private DataInputStream in;
    private PrintStream out;


    /**
     * constructor
     */
    public Player(Socket playerSocket, Server server) {
        this.playerSocket = playerSocket;
        this.server = server;

        try {
            in = new DataInputStream(playerSocket.getInputStream());
            out = new PrintStream(playerSocket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void run() {

        prompt = new Prompt(in, out);
    }

    public synchronized int limitPlayers() {

        prompt = new Prompt(in, out);
        IntegerInputScanner scanner = new IntegerInputScanner();
        scanner.setMessage("How many players? ");

        return prompt.getUserInput(scanner);
    }

    public String askUsername() {

        display();

        prompt = new Prompt(in, out);

        StringInputScanner scanner = new StringInputScanner();
        scanner.setMessage("Enter your username: ");

        String username = prompt.getUserInput(scanner);

        // this will print to the server
        System.out.println("Username: " + username);

        PrintStream writer = new PrintStream(out);
        writer.println("\n** Welcome, " + username + "! **\n");

        return username;
    }

    public void display() {

        String javarizer = "\n" +
                "     __                          .__                     \n" +
                "    |__|____ ___  _______ _______|__|_______ ___________ \n" +
                "    |  \\__  \\\\  \\/ /\\__  \\\\_  __ \\  \\___   // __ \\_  __ \\\n" +
                "    |  |/ __ \\\\   /  / __ \\|  | \\/  |/    /\\  ___/|  | \\/\n" +
                "/\\__|  (____  /\\_/  (____  /__|  |__/_____ \\\\___  >__|   \n" +
                "\\______|    \\/           \\/               \\/    \\/       \n";


        out.println(javarizer);
    }

    /**
     * method to increment player's score
     */
    public void incrementScore() {
        this.score++;
    }

    /**
     * getters
     */
    public PrintStream getOut() {
        return out;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public boolean isFinished() {
        return finished;
    }

    /**
     * setters
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
