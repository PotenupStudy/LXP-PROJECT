package com.lxp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {

    private static final HikariDataSource dataSource;
    static {

        try{
            Properties props = new Properties();

            props.load(JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties"));
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            config.setMaximumPoolSize(10); // 커넥션은 최대 10개 허용

            config.setMinimumIdle(5); // 최소한 5개의 유휴 상태를 두겠다.

            config.setIdleTimeout(30000); // 30초 동안 유휴 상태이면 커넥션을 닫는다.

            // 데이터베이스 커넥션이 생성된 후 사용할 수 있는 최대 시간.
            // 만약 해당 시간을 초과하면 새로운 객체로 변경해서 지원한다.
            // 이는 시간이 지나면 connection이 불안정할 수 있기 때문이다.
            config.setMaxLifetime(1800000);

            //연결 요청이 2초이상 지연되면 연결 실패로 인식
            //이는 스레드가 고갈되는 현상을 방지하기 위함이다.
            config.setConnectionTimeout(2000);

            dataSource = new HikariDataSource(config);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {

        /*
         * DB에는 세션이라는 것이 존재하며 이러한 세션은 DB에 연결한 시점으로 생성된다.
         * 이를 기점으로 트랜잭션 임시 데이터, 캐싱 등의 데이터를 관리하게 된다.
         *
         * 우리가 사용하는 hikari의 경우 db커넥션 객체를 몇 개 생성하고
         * 다른 사용자에게 빌려주고 반환받고 다시 빌려주는 형식으로 동작되는데
         * 이러한 과정에서 세션이 중복되는 문제가 발생할 수 있기 때문에 jdbc내부에서 세션을 새롭게 초기화한다.
         * */
        return dataSource.getConnection();
    }

    public static void close(){
        if(dataSource != null){
            dataSource.close();
        }
    }

    public static void printConnectionPoolStatus(){
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
        System.out.println("hikaricp 커넥션 풀 상태");
        System.out.println("총 커넥션 수 : " + poolMXBean.getTotalConnections());
        System.out.println("활성 커넥션 수 : " +  poolMXBean.getActiveConnections());
        System.out.println("유휴 커넥션 수 : " + poolMXBean.getIdleConnections());
        System.out.println("대기 중인 커넥션 요청 수 : " + poolMXBean.getThreadsAwaitingConnection());
    }
}
