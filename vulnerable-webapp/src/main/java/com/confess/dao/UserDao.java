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
package com.confess.dao;

import com.confess.entity.User;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DAO for user entity.
 *
 * @author Jakob Korherr
 */
@ManagedBean
@ApplicationScoped
public class UserDao
{

    @ManagedProperty("#{connectionBean}")
    private ConnectionBean connectionBean;

    public User getUser(String username, String password) throws SQLException
    {
        String query = "SELECT id, username, password, email FROM user "
                + "WHERE username='" + username + "' AND password='" + password + "'";

        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            statement = connectionBean.getConnection().createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next())
            {
                User user = new User();

                user.setId(resultSet.getLong(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));

                return user;
            }
        }
        finally
        {
            if (resultSet != null)
            {
                resultSet.close();
            }
            if (statement != null)
            {
                statement.close();
            }
        }

        return null;
    }

    public User getUser(String email) throws SQLException
    {
        String query = "SELECT id, username, password, email FROM user "
                + "WHERE email='" + email + "';";

        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            statement = connectionBean.getConnection().createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next())
            {
                User user = new User();

                user.setId(resultSet.getLong(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));

                return user;
            }
        }
        finally
        {
            if (resultSet != null)
            {
                resultSet.close();
            }
            if (statement != null)
            {
                statement.close();
            }
        }

        return null;
    }

    public ConnectionBean getConnectionBean()
    {
        return connectionBean;
    }

    public void setConnectionBean(ConnectionBean connectionBean)
    {
        this.connectionBean = connectionBean;
    }
}
