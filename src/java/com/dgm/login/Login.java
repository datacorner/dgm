/*
 * Copyright (C) 2016 Benoit CAYLA <benoit@famillecayla.fr>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dgm.login;

import com.joy.mvc.actionTypes.ActionTypeLogin;

/**
 *
 * @author Benoit CAYLA <benoit@famillecayla.fr>
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
