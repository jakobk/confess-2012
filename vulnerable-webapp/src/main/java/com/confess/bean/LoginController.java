/*
 * Copyright 2011-2012, Jakob Korherr
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.confess.bean;

import com.confess.dao.UserDao;
import com.confess.entity.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Login controller.
 *
 * @author Jakob Korherr
 */
@ManagedBean
@RequestScoped
public class LoginController implements Serializable
{

    private static final Logger log = Logger.getLogger(LoginController.class.getName());

    @ManagedProperty("#{userBean}")
    private UserBean userBean;

    @ManagedProperty("#{userDao}")
    private UserDao userDao;

    private String username;
    private String password;
    private String email;

    public String login()
    {
        User user = null;
        try
        {
            user = userDao.getUser(username, password);
        }
        catch (SQLException e)
        {
            log.log(Level.WARNING, "SQLException", e);
        }

        if (user != null)
        {
            userBean.setUser(user);
            userBean.setAuthenticated(true);

            return "/pages/internal/hello.xhtml?faces-redirect=true";
        }

        return "/pages/login.xhtml?msg=Invalid login data&faces-redirect=true";
    }

    public String logout()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();

        return "/pages/login.xhtml?msg=Successfully logged out&faces-redirect=true";
    }

    public String sendEmail()
    {
        User user = null;
        try
        {
            user = userDao.getUser(email);
        }
        catch (SQLException e)
        {
            log.log(Level.WARNING, "SQLException", e);
        }

        if (user != null)
        {
            // TODO send email containing password to user

            return "/pages/forgot_password.xhtml?msg=E-mail sent, please check your account&faces-redirect=true";
        }

        return "/pages/forgot_password.xhtml?msg=Invalid e-mail address&faces-redirect=true";
    }

    public UserBean getUserBean()
    {
        return userBean;
    }

    public void setUserBean(UserBean userBean)
    {
        this.userBean = userBean;
    }

    public UserDao getUserDao()
    {
        return userDao;
    }

    public void setUserDao(UserDao userDao)
    {
        this.userDao = userDao;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
