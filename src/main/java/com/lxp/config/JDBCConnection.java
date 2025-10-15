package com.lxp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/*
 * resources -> config.properties
 *
 * 외부 파일로 분리하는 것은 우리가 자바의 소스코드에 리터럴 값으로
 * DB 엑세스 정보를 저장하는 경우 외부에서 우리의 DB 정보를 확인할 수 있는 문제가 발생한다.
 * 이를 방지하기 위해 우리는 외부 파일로 접근 정보를 분리하고 해당 파일을 참고하는 방식으로 작성하게 되며
 * 이를 통해 데이터의 직접적인 조회를 막을 수 있게 된다.
 */
public class JDBCConnection {

    private static final HikariDataSource dataSource;

    private JDBCConnection() {
    }

    static {
        try {
            Properties properties = new Properties();

            // 1. resources/config.properties 파일 로드
            properties.load(JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties"));

            HikariConfig config = new HikariConfig();

            // 2. 설정 객체에 DB 연결 정보 설정
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));

            // 커넥션 풀 설정
            // 커넥션은 최대 10개 허용
            config.setMaximumPoolSize(10);
            // 최소한 5개의 유휴 상태를 두겠다.
            config.setMinimumIdle(5);
            // 30초 동안 유휴 상태이면 커넥션을 닫는다. (30000ms)
            config.setIdleTimeout(30000);
            // 데이터베이스 커넥션이 생성된 후 사용할 수 있는 최대시간 (1,800,000ms = 30분)
            config.setMaxLifetime(1800000);

            // 연결 요청 2초 이상이면 연결 실패 쓰레드가 고갈되는 현상을 방지 (2000ms)
            config.setConnectionTimeout(2000);

            dataSource = new HikariDataSource(config);

        } catch (IOException e) {
            // 파일을 찾지 못하거나 로드에 실패하면 런타임 예외 발생
            throw new RuntimeException("Failed to load config.properties", e);
        } catch (Exception e) {
            // HikariDataSource 초기화 중 발생하는 다른 예외 처리
            throw new RuntimeException("Failed to initialize HikariDataSource", e);
        }
    }

    /**
     * 커넥션 풀에서 DB Connection 객체를 가져옵니다.
     * @return DB Connection
     * @throws SQLException DB 연결 오류 발생 시
     */
    public static Connection getConnection() throws SQLException {
        /*
         * DB에는 세션이라는 것이 존재하며 이러한 세션은 DB에 연결한 시점을 생성된다.
         * 이를 기점으로 트랜잭션 임시 데이터, 캐싱 등의 데이터를 관리하게 된다.
         *
         * 우리가 사용하는 hikari의 경우 DB 커넥션 객체를 몇개 생성하고
         * 다른 사용자에게 빌려주고 반환 받고 다시 빌려주는 형식으로 동작되는데
         * 이러한 과정에서 세션이 중복되는 문제가 발생할 수 있기 때문에 JDBC 내부에서 세션을 새롭게 초기화한다.
         */
        return dataSource.getConnection();
    }

    /**
     * HikariCP 커넥션 풀을 닫습니다. (애플리케이션 종료 시 호출 권장)
     */
    public static void close() {
        if (dataSource != null) {
            dataSource.close();
            System.out.println("HikariCP Connection Pool closed.");
        }
    }

    /**
     * 현재 커넥션 풀의 상태를 출력합니다.
     */
    public static void printConnectionPoolStatus() {
        if (dataSource == null) {
            System.out.println("HikariDataSource is not initialized.");
            return;
        }

        HikariPoolMXBean hikariPoolMXBean = dataSource.getHikariPoolMXBean();
        System.out.println("--- 커넥션 풀 상태 ---");
        System.out.println("Total Connections (전체) = " + hikariPoolMXBean.getTotalConnections());
        System.out.println("Active Connections (사용 중) = " + hikariPoolMXBean.getActiveConnections());
        System.out.println("Idle Connections (유휴 상태) = " + hikariPoolMXBean.getIdleConnections());
        System.out.println("Threads Awaiting Connection (대기 중인 쓰레드) = " + hikariPoolMXBean.getThreadsAwaitingConnection());
        System.out.println("--------------------");
    }
}
