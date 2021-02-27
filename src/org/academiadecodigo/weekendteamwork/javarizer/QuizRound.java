package org.academiadecodigo.weekendteamwork.javarizer;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class QuizRound implements Runnable{

    private final String path = "resources/quiz.txt";

    private FileReader fileReader;
    private BufferedReader bReader;
    private List<String> list;
    private Player player;
    private Server server;


    public QuizRound(Player player, Server server) {
        this.player = player;
        list = new LinkedList<>();
        this.server = server;
        initIO();
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
                int correctAnswer = Integer.parseInt(list.get(counter++));

                // menu
                String[] menu = {answer1, answer2, answer3, answer4};

                MenuInputScanner menuInputScanner = new MenuInputScanner(menu);
                menuInputScanner.setMessage(question);

                int playersAnswer = player.getPrompt().getUserInput(menuInputScanner);

                //wait();

                // if answer is correctAnswer, increase score

                if (playersAnswer == correctAnswer) {
                    player.incrementScore();
                    System.out.println("score " + player.getScore());
                }
                player.getOut().println("correct answer is: " + correctAnswer);
            }

            /**
             * show results
             * falta fazer
             */

            player.getOut().close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        round();
    }
}