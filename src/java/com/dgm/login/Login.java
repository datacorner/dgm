/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.login;

import com.joy.mvc.actionTypes.ActionTypeLogin;

/**
 *
 * @author benoit
 */
public class Login extends ActionTypeLogin {

    @Override
    public String login() {
        String User = this.getStrArgumentValue("user");
        String Password = this.getStrArgumentValue("password");
        this.getCurrentSession().setAttribute("user", User);
        
        return super.login();
    }

    @Override
    public String request() {
        return super.request(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String logout() {
        return super.logout(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
