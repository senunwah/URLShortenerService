package com.interswitch.URLShorteningService.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceConfig {

    private static final String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=USSDb";
    private static final String username = "sa";
    private static final String password = "welcome12@";

    public static DriverManagerDataSource getDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }


}
