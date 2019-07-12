/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Controller.GameController;
import Model.Question;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class GameForm extends JFrame implements ActionListener {

//    private JFrame frame;
    private JLabel labelQuestion;
    private JLabel labelFinishMsg;
    private JLabel labelStandByMsg;
    private JLabel labelResultMsg;
    
    private JButton btnAnswerA;
    private JButton btnAnswerB;
    private JButton btnAnswerC;
    private JButton btnAnswerD;
    private JProgressBar progressBar;
    private JPanel panel;
    private GameController controller;

//    private ObjectInputStream in;
//    private ObjectOutputStream out;
    public GameForm(GameController control) {
        controller = control;
//        in = input;
//        out = output;
//        this.frame = new JFrame();
        btnAnswerA = new JButton();
        btnAnswerA.setPreferredSize(new Dimension(90, 40));
        btnAnswerA.addActionListener(this);
        btnAnswerB = new JButton();
        btnAnswerB.setPreferredSize(new Dimension(90, 40));
        btnAnswerB.addActionListener(this);
        btnAnswerC = new JButton();
        btnAnswerC.setPreferredSize(new Dimension(90, 40));
        btnAnswerC.addActionListener(this);
        btnAnswerD = new JButton();
        btnAnswerD.setPreferredSize(new Dimension(90, 40));
        btnAnswerD.addActionListener(this);

        labelQuestion = new JLabel();
        labelQuestion.setFont(new Font("Serif", Font.PLAIN, 40));
        labelQuestion.setForeground(Color.black);
        labelQuestion.setHorizontalAlignment(JLabel.CENTER);
        labelQuestion.setVerticalAlignment(JLabel.CENTER);
        
        
        labelResultMsg = new JLabel();
        labelResultMsg.setFont(new Font("Serif", Font.PLAIN, 25));
        labelResultMsg.setForeground(Color.black);
        labelResultMsg.setHorizontalAlignment(JLabel.CENTER);
        labelResultMsg.setVerticalAlignment(JLabel.CENTER);
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(250, 10, 170, 20);
        progressBar.setStringPainted(true);
        
        labelStandByMsg = new JLabel();
        labelStandByMsg.setFont(new Font("Serif", Font.PLAIN, 25));
        labelStandByMsg.setForeground(Color.black);
        labelStandByMsg.setHorizontalAlignment(JLabel.CENTER);
        labelStandByMsg.setVerticalAlignment(JLabel.CENTER);
        labelStandByMsg.setText("đợi người chơi");
        
        labelFinishMsg = new JLabel();
        labelFinishMsg.setFont(new Font("Serif", Font.PLAIN, 25));
        labelStandByMsg.setHorizontalAlignment(JLabel.CENTER);
        labelStandByMsg.setVerticalAlignment(JLabel.CENTER);
        labelFinishMsg.setText("bạn đã trả lời hết câu hỏi");
        
        // size of button
        int btnWidth = 90;
        int btnHeight = 40;

        // set location for button
        btnAnswerA.setBounds(80, 200, btnWidth, btnHeight);
        btnAnswerB.setBounds(270, 200, btnWidth, btnHeight);
        btnAnswerC.setBounds(80, 270, btnWidth, btnHeight);
        btnAnswerD.setBounds(270, 270, btnWidth, btnHeight);
        labelQuestion.setBounds(150, 50, 140, 70);
        labelStandByMsg.setBounds(105, 50 , 250, 70);
        labelResultMsg.setBounds(105, 50 , 250, 70);
        labelFinishMsg.setBounds(105, 50, 250, 70);
        
        panel = new JPanel();
        
        panel.setLayout(null);
        panel.add(labelStandByMsg);
//        panel.add(btnAnswerA);
//        panel.add(btnAnswerB);
//        panel.add(btnAnswerC);
//        panel.add(btnAnswerD);
//        panel.add(labelQuestion);
//        panel.add(progressBar);

        this.getContentPane().add(panel);
    }

    public class timeThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i <= 100; i++) {
                progressBar.setValue(i);
                progressBar.repaint();
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                }
            }

            if (progressBar.getValue() == 100) {
                controller.timeOut();
            }
        }
    }

    public void displayNewQuestion(Question question) {
        int operation = question.getOperation(),
                a = question.getA(),
                b = question.getB(),
                c = question.getResult();
        Random rand = new Random();
        int resultBtn = rand.nextInt(4);
        switch (operation) {
            case 0:
                labelQuestion.setText(a + " + " + b);
                break;
            case 1:
                labelQuestion.setText(a + " - " + b);
                break;
            case 2:
                labelQuestion.setText(a + " * " + b);
                break;
            case 3:
                labelQuestion.setText(a + " / " + b);
                break;
        }
        switch (resultBtn) {
            case 0:
                btnAnswerA.setText("" + c);
                btnAnswerB.setText("" + rand.nextInt(100));
                btnAnswerC.setText("" + rand.nextInt(100));
                btnAnswerD.setText("" + rand.nextInt(100));
                break;
            case 1:
                btnAnswerA.setText("" + rand.nextInt(100));
                btnAnswerB.setText("" + c);
                btnAnswerC.setText("" + rand.nextInt(100));
                btnAnswerD.setText("" + rand.nextInt(100));
                break;
            case 2:
                btnAnswerA.setText("" + rand.nextInt(100));
                btnAnswerB.setText("" + rand.nextInt(100));
                btnAnswerC.setText("" + c);
                btnAnswerD.setText("" + rand.nextInt(100));
                break;
            case 3:
                btnAnswerA.setText("" + rand.nextInt(100));
                btnAnswerB.setText("" + rand.nextInt(100));
                btnAnswerC.setText("" + rand.nextInt(100));
                btnAnswerD.setText("" + c);
                break;
        }
    }

    public void displayGameForm() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(460, 400));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        new Thread(new timeThread()).start();
    }

    public void destroyGameForm() {
        this.dispose();
    }

    public void displayFinishMsg() {
        panel.remove(btnAnswerA);
        panel.remove(btnAnswerB);
        panel.remove(btnAnswerC);
        panel.remove(btnAnswerD);
        panel.remove(labelQuestion);
        panel.add(labelFinishMsg);
        panel.revalidate();
        panel.repaint();
    }
    
    public void displayTheGame() {
        panel.removeAll();
        panel.add(btnAnswerA);
        panel.add(btnAnswerB);
        panel.add(btnAnswerC);
        panel.add(btnAnswerD);
        panel.add(labelQuestion);
        panel.add(progressBar);
        panel.revalidate();
        panel.repaint();
    }
    
    public void displayResultMsg(int score) {
        panel.removeAll();
        labelResultMsg.setText("bạn đã làm đúng " + score + " câu ");
        panel.add(labelResultMsg);
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAnswerA) {
            controller.buttonPressed(String.valueOf(btnAnswerA.getText()));
        }
        if (e.getSource() == btnAnswerB) {
            controller.buttonPressed(String.valueOf(btnAnswerB.getText()));
        }
        if (e.getSource() == btnAnswerC) {
            controller.buttonPressed(String.valueOf(btnAnswerC.getText()));
        }
        if (e.getSource() == btnAnswerD) {
            controller.buttonPressed(String.valueOf(btnAnswerD.getText()));
        }

    }

}
