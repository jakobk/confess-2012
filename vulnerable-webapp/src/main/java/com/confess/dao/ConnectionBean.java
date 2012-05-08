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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bean for DB connection.
 *
 * @author Jakob Korherr
 */
@ManagedBean
@ApplicationScoped
public class ConnectionBean
{

    private static Logger log = Logger.getLogger(ConnectionBean.class.getName());

    private static final String DRIVER = "org.gjt.mm.mysql.Driver";
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/confess?allowMultiQueries=true";
    private static final String DB_USER = "root";
    private static final String DB_PWD = "confess2012";

    private Connection connection;

    @PostConstruct
    private void init()
    {
        try
        {
            // load driver
            Class.forName(DRIVER);

            // establish connection
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PWD);
        }
        catch (ClassNotFoundException e)
        {
            log.log(Level.SEVERE, "Could not load database driver", e);
            throw new IllegalStateException(e);
        }
        catch (SQLException e)
        {
            log.log(Level.SEVERE, "Error connecting to database", e);
            throw new IllegalStateException(e);
        }
    }

    @PreDestroy
    private void destroy()
    {
        if (connection != null)
        {
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {
                log.log(Level.SEVERE, "Could not close database connection.", e);
            }
            connection = null;
        }
    }

    public Connection getConnection()
    {
        return connection;
    }

}
