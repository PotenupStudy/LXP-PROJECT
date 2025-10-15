package com.lxp.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {
    public static final HikariDataSource dataSource;
    static {
        try{
            Properties properties = new Properties();
            properties.load(JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties"));

            HikariDataSource config = new HikariDataSource();
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));

            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(2000);

            dataSource = config;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(){
        if(dataSource != null)
            dataSource.close();
    }

    public static void printConnectionPooStatus(){
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
        System.out.println("hikaricp 커넥션 풀 상태");
        System.out.println("총 커넥션 갯수 : " + poolMXBean.getTotalConnections());
        System.out.println("활성 커넥션 갯수 : " + poolMXBean.getActiveConnections());
        System.out.println("유휴 커넥션 갯수 : " + poolMXBean.getIdleConnections());
        System.out.println("대기 커넥션 갯수 : " + poolMXBean.getThreadsAwaitingConnection());
    }
}