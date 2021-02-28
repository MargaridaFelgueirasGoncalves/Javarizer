package org.academiadecodigo.weekendteamwork.javarizer;

import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class QuizRound implements Runnable{

    /**
     * fields
     */
    private final String path = "resources/quiz.txt";
    private String beforeMenu;

    private FileReader fileReader;
    private BufferedReader bReader;
    private List<String> list;

    private Player player;
    private Server server;

    /**
     * constructor
     * @param player
     * @param server
     */
    public QuizRound(Player player, Server server) {

        this.player = player;
        list = new LinkedList<>();
        this.server = server;
        initIO();
        beforeMenu = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    }

    public void initIO() {

        try {
            fileReader = new FileReader(path);
            bReader = new BufferedReader(fileReader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void round() {

        try {
            String line = "";

            while ((line = bReader.readLine()) != null) {
                list.add(line);
            }

            int counter = 0;

            while (counter < list.size()) {

                String question = list.get(counter++);
                String answer1 = list.get(counter++);
                String answer2 = list.get(counter++);
                String answer3 = list.get(counter++);
                String answer4 = list.get(counter++);
                int numberCorrectAnswer = Integer.parseInt(list.get(counter).split("-")[0]);
                String correctAnswer = list.get(counter++).split("-")[1];

                // menu
                String[] menu = {answer1, answer2, answer3, answer4};

                MenuInputScanner menuInputScanner = new MenuInputScanner(menu);
                menuInputScanner.setMessage(question);

                player.getOut().println(beforeMenu);
                int playersAnswer = player.getPrompt().getUserInput(menuInputScanner);

                // if answer is correctAnswer, increase score
                if (playersAnswer == numberCorrectAnswer) {
                    player.incrementScore();
                    System.out.println("score " + player.getScore());
                }
                player.getOut().println("\n>> Correct answer: " + correctAnswer + "\n");

                if (counter == list.size()){
                    player.setFinished(true);

                    drawingGameOver();
                }
            }

            getResults();
            displayResults();

//            player.getOut().close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        round();
    }

    public void drawingGameOver() {

        player.getOut().println("\n\n                        .-\"\"\"-.\n" +
                "                       / .===. \\\n" +
                "                       \\/ 6 6 \\/\n" +
                "                       ( \\___/ )\n" +
                "  _________________ooo__\\_____/_____________________\n" +
                " /                                                  \\\n" +
                "|            Game Over... Waiting for results!       |\n" +
                " \\______________________________ooo_________________/\n" +
                "                       |  |  |\n" +
                "                       |_ | _|\n" +
                "                       |  |  |\n" +
                "                       |__|__|\n" +
                "                       /-'Y'-\\\n" +
                "                      (__/ \\__)\n\n");
    }

    public void getResults(){

        for (Player player: server.getPlayersList()) {
            if(!player.isFinished()){
                getResults();
            }
        }
    }

    public void displayResults() {

        for (Player player : server.getPlayersList()) {
            server.broadcast(player.getUsername().toUpperCase(Locale.ROOT) + ": " + player.getScore() + " correct answers.");
        }
    }
}
