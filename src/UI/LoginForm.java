/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Controller.LoginController;
import Model.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends JFrame implements ActionListener{
    
    private JButton btnLogin;
    private JButton btnCancel;
    private JTextField txtUser;
    private JPasswordField txtpassPassword;
    private JLabel labelUser;
    private JLabel labelPassword;
    
    private LoginController controller;

    public LoginForm(LoginController control){
        this.controller = control;
        
        btnLogin = new JButton("Login");
        btnLogin.setBorder(null);
        btnLogin.setBackground(new Color(200, 200, 200));
        btnLogin.addActionListener(this);
        btnCancel = new JButton("Cancel");
        btnCancel.setBorder(null);
        btnCancel.setBackground(new Color(200, 200, 200));
        btnCancel.addActionListener(this);
        labelUser = new JLabel("User Name");
        labelPassword = new JLabel("Password");
        txtUser = new JTextField();
        txtpassPassword = new JPasswordField();
        
        labelUser.setBounds(60, 40, 80, 30);
        labelPassword.setBounds(60, 80, 80, 30);
        txtUser.setBounds(150, 40, 200, 30);
        txtpassPassword.setBounds(150, 80, 200,30);
        btnLogin.setBounds(120, 140, 65, 30);
        btnCancel.setBounds(250, 140, 65, 30);
        
        JPanel panel = new JPanel();
        
        panel.setLayout(null);
        panel.add(btnCancel);
        panel.add(btnLogin);
        panel.add(labelPassword);
        panel.add(labelUser);
        panel.add(txtpassPassword);
        panel.add(txtUser);
        
        this.getContentPane().add(panel);
    }
    
    public void displayLoginForm(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(440, 250));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);    
    }
    
    public void displayErorr(){
        JOptionPane.showMessageDialog(this, "username hoặc password không đúng");
    }
    
    public void displayAlreadyLogined() {
        JOptionPane.showMessageDialog(this, "user này đã được đăng nhập");
    }
    
    public void desTroyLoginForm(){
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == btnLogin){
            String username = String.valueOf(txtUser.getText());
            String password = String.valueOf(txtpassPassword.getPassword());
            User user = new User(username, password);
            controller.login(user);
        }
        if(ae.getSource() == btnCancel){
            desTroyLoginForm();
        }
    }
    
}
