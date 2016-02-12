package io.beanmapper.config;

import javax.sql.DataSource;

public interface Profile {

    DataSource dataSource();
    String hibernateDialect();
    String hibernate2Ddl();
}
