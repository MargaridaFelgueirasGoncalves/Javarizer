package org.academiadecodigo.weekendteamwork.javarizer.server;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class QuizRound {

    private final String path = "resources/quiz.txt";

    private FileReader fileReader;
    private BufferedReader bReader;
    private List<String> list;
    private Prompt prompt;


    public QuizRound(Prompt prompt) {

        this.prompt = prompt;
        list = new LinkedList<>();
        initIO();
        round();
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

                int playersAnswer = prompt.getUserInput(menuInputScanner);

                // if answer is correctAnswer, increase score
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
