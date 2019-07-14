/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ProjectStatus;
import Model.Question;
import UI.GameForm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GameController {

    private GameForm gameView;
    private ArrayList<Question> questions;
    private ObjectOutputStream out;
    private int currentQuestion = 0;
    private int score = 0;

    public GameController(ObjectOutputStream out) {
        this.gameView = new GameForm(this);
        this.out = out;
    }

    public void showGameForm() {
        gameView.displayGameForm();
    }

    public void hideGameForm() {
        gameView.destroyGameForm();
    }

    public void initData(ArrayList<Question> questions) {
        this.questions = questions;
        gameView.displayNewQuestion(questions.get(currentQuestion));
    }
    
    public void showTheGame() {
        gameView.displayTheGame();
    }
    
    public void showResult(int score, String declaredMsg) {
        gameView.displayResultMsg(score, declaredMsg);
    }

    public void buttonPressed(String result) {
        if (Integer.parseInt(result) == questions.get(currentQuestion).getResult()) {
            score++;
        }
        currentQuestion++;
        if (currentQuestion > questions.size() - 1) {
            gameView.displayFinishMsg();
        } else {
            gameView.displayNewQuestion(questions.get(currentQuestion));
        }
    }

    public void sendScore() {
        try {
            out.writeObject(score);
        } catch (IOException e) {
        }
    }

    public void timeOut() {
        try {
            out.writeObject(ProjectStatus.TIMEOUT);
            System.out.println("TIME OUT");
            out.writeObject(score);
        } catch (Exception e) {
        }
    }
}
