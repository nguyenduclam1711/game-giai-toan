/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_giai_toan;

import Controller.GameController;
import Controller.LoginController;
import Model.ProjectStatus;
import Model.Question;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private ArrayList<Question> questions;

    private GameController gameController;
    private LoginController loginController;

    public void runClient() throws IOException, ClassNotFoundException {
        try {
            socket = new Socket("localhost", 9999);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            loginController = new LoginController(out);
            loginController.showLoginForm();

            // nhan thong bao dang nhap tu server
            while (true) {
                ProjectStatus status = (ProjectStatus) in.readObject();
                if (status == ProjectStatus.SUCCESS) {
                    loginController.hideLoginForm();
                    break;
                } else if (status == ProjectStatus.ERROR) {
                    loginController.showErorrLogin();
                } else {
                    loginController.showAlreadyLogined();
                }
            }
            
            gameController = new GameController(out);
            gameController.showGameForm();
            ProjectStatus status = null;
            
            while(true){
                status = (ProjectStatus) in.readObject();
                if (status == ProjectStatus.PLAY) {
                    
                    break;
                }
            }
            
            questions = (ArrayList<Question>) in.readObject();
            //hien thi phan choi game
            gameController.showGameForm();
            gameController.showTheGame();
            gameController.initData(questions);
            
            status = (ProjectStatus) in.readObject();
            if (status == ProjectStatus.SENDRESULT) {
                System.out.println("RECCEIVED RESULT");
                int score = (int) in.readObject();;
                gameController.showResult(score);
            }
            

//            gameController.showGameForm();
//            ArrayList<Question> questions = (ArrayList<Question>) in.readObject();
//            //hien thi phan choi game
//            gameController.showTheGame();
//            gameController.initData(questions);
//            status = (ProjectStatus) in.readObject();
//            if (status == ProjectStatus.SENDRESULT) {
//                System.out.println("RECCEIVED RESULT");
//                int score = (int) in.readObject();;
//                gameController.showResult(score);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        client.runClient();
    }
}
