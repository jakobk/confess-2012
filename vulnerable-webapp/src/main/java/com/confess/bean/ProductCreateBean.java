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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bean for product create page.
 *
 * @author Jakob Korherr
 */
@ManagedBean
@ViewScoped
public class ProductCreateBean
{

    private static final Logger log = Logger.getLogger(ProductCreateBean.class.getName());

    @ManagedProperty("#{productDao}")
    private ProductDao productDao;

    private int productId;
    private String productName;
    private String productDescription;
    private int productPrice;
    private String action;

    public void preRenderProductCreatePage(ComponentSystemEvent event)
    {
        if ("create".equals(action))
        {
            Product product = new Product();
            product.setId(productId);
            product.setName(productName);
            product.setDescription(productDescription);
            product.setPrice(productPrice);

            try
            {
                productDao.createProduct(product);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfully created product."));
            }
            catch (SQLException e)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error creating product."));
            }
        }
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductDescription()
    {
        return productDescription;
    }

    public void setProductDescription(String productDescription)
    {
        this.productDescription = productDescription;
    }

    public int getProductPrice()
    {
        return productPrice;
    }

    public void setProductPrice(int productPrice)
    {
        this.productPrice = productPrice;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
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
