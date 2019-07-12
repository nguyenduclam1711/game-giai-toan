/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import UI.LoginForm;
import java.io.ObjectOutputStream;



public class LoginController {
    
    private ObjectOutputStream out;
    private LoginForm loginView;
    
    public LoginController(ObjectOutputStream out){
        this.loginView = new LoginForm(this);
        this.out = out;
    }
    
    public void showLoginForm(){
        loginView.displayLoginForm();
    }
    
    public void hideLoginForm(){
        loginView.desTroyLoginForm();
    }
    
    
    public void login(User user){
        try {
            out.writeObject(user);
        } catch (Exception e) {
            System.out.println("LoginFormError: Cant pass object to server");
        }
    }
    
    public void showErorrLogin(){
        loginView.displayErorr();
    }
    
    public void showAlreadyLogined() {
        loginView.displayAlreadyLogined();
    }
    
}
