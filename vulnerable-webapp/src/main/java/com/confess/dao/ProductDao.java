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

import com.confess.entity.Product;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * DAO for Product entity.
 *
 * @author Jakob Korherr
 */
@ManagedBean
@ApplicationScoped
public class ProductDao
{

    @ManagedProperty("#{connectionBean}")
    private ConnectionBean connectionBean;

    public void updateProduct(Product product) throws SQLException
    {
        String query = "UPDATE product SET name='" + product.getName() + "',"
                + " description='" + product.getDescription() + "',"
                + " price=" + product.getPrice() + " WHERE id=" + product.getId() + ";";

        Statement statement = null;
        try
        {
            statement = connectionBean.getConnection().createStatement();
            statement.executeUpdate(query);
        }
        finally
        {
            if (statement != null)
            {
                statement.close();
            }
        }
    }

    public void createProduct(Product product) throws SQLException
    {
        String query = "INSERT INTO product (id, name, description, price) "
                + "VALUES (" + product.getId() + ","
                + "'" + product.getName() + "',"
                + "'" + product.getDescription() + "',"
                + product.getPrice() + ");";

        Statement statement = null;
        try
        {
            statement = connectionBean.getConnection().createStatement();
            statement.executeUpdate(query);
        }
        finally
        {
            if (statement != null)
            {
                statement.close();
            }
        }
    }


    public List<Product> getProducts(String filter) throws SQLException
    {
        String query = "SELECT id, name, description, price FROM product "
                + "WHERE name LIKE '%" + filter + "%';";

        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            statement = connectionBean.getConnection().createStatement();
            resultSet = statement.executeQuery(query);

            List<Product> list = new LinkedList<Product>();
            while (resultSet.next())
            {
                Product product = new Product();

                product.setId(resultSet.getLong(1));
                product.setName(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                product.setPrice(resultSet.getLong(4));

                list.add(product);
            }
            return list;
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
    }

    public Product getProduct(String productId) throws SQLException
    {
        String query = "SELECT id, name, description, price FROM product "
                + "WHERE id=" + productId + ";";

        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            statement = connectionBean.getConnection().createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next())
            {
                Product product = new Product();

                product.setId(resultSet.getLong(1));
                product.setName(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                product.setPrice(resultSet.getLong(4));

                return product;
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
