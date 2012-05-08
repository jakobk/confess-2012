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

import com.confess.dao.ProductDao;
import com.confess.entity.Product;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Bean for product pages.
 *
 * @author Jakob Korherr
 */
@ManagedBean
@ViewScoped
public class ProductBean
{

    private static final Logger log = Logger.getLogger(ProductBean.class.getName());

    @ManagedProperty("#{productDao}")
    private ProductDao productDao;

    private List<Product> products;
    private String filter;

    @PostConstruct
    private void init()
    {
        filter = "";
        reloadProducts();
    }

    public void applyFilter(ActionEvent event)
    {
        reloadProducts();
    }

    private void reloadProducts()
    {
        try
        {
            products = productDao.getProducts(filter);
        }
        catch (SQLException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            products = Collections.emptyList();
        }
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public String getFilter()
    {
        return filter;
    }

    public void setFilter(String filter)
    {
        this.filter = filter;
    }

    public ProductDao getProductDao()
    {
        return productDao;
    }

    public void setProductDao(ProductDao productDao)
    {
        this.productDao = productDao;
    }
}
