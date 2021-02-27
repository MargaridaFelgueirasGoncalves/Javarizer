package org.academiadecodigo.weekendteamwork.javarizer.player;

import org.academiadecodigo.weekendteamwork.javarizer.server.ConnectionHandler;

public class Player {

    /**
     * fields
     */
    private String username;
    private int score;
    private ConnectionHandler connectionHandler;

    /**
     * constructor
     */
    public Player(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }


    /**
     * getters and setters
     */
    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
