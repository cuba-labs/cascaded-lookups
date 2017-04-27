/*
 * Copyright (c) 2016 cascaded-lookups
 */
package com.company.cascadedlookups.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

/**
 * @author knst
 */
@NamePattern("%s|name")
@Table(name = "DEMO_COUNTRY")
@Entity(name = "demo$Country")
public class Country extends StandardEntity {
    private static final long serialVersionUID = -751751634725269273L;

    @Column(name = "NAME")
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}