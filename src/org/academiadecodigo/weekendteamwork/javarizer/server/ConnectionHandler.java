package org.academiadecodigo.weekendteamwork.javarizer.server;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import java.io.*;
import java.net.Socket;

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
        askUsername();
    }

    public void askUsername() {

        StringInputScanner scanner = new StringInputScanner();
        scanner.setMessage("Enter your username: ");

        String username = prompt.getUserInput(scanner);

        // this will print to the server
        System.out.println("Username: " + username);
    }

}
