package org.academiadecodigo.weekendteamwork.javarizer.server;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ConnectionHandler implements Runnable {

    /**
     * fields
     */
    private Socket playerSocket;
    private Server server;
    private Prompt prompt;
    private DataInputStream in;
    private PrintStream out;

    /**
     * constructor
     */
    public ConnectionHandler(Socket playerSocket, Server server) {
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
//        display();
//        askUsername();
        menu();
    }

    public void askUsername() {

        StringInputScanner scanner = new StringInputScanner();
        scanner.setMessage("Enter your username: ");

        String username = prompt.getUserInput(scanner);

        // this will print to the server
        System.out.println("Username: " + username);
    }

    public void display() {

        String javarizer = "\n" +
                "     __                          .__                     \n" +
                "    |__|____ ___  _______ _______|__|_______ ___________ \n" +
                "    |  \\__  \\\\  \\/ /\\__  \\\\_  __ \\  \\___   // __ \\_  __ \\\n" +
                "    |  |/ __ \\\\   /  / __ \\|  | \\/  |/    /\\  ___/|  | \\/\n" +
                "/\\__|  (____  /\\_/  (____  /__|  |__/_____ \\\\___  >__|   \n" +
                "\\______|    \\/           \\/               \\/    \\/       \n";
        System.out.println(javarizer);
    }

    public void menu() {

        // display each question in a menu
        new QuizRound(prompt);
    }

    public PrintStream getOut() {
        return out;
    }
}
