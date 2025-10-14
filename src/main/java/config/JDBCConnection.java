package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {
    private static final HikariDataSource ds;

    static {
        try {
            Properties prop = new Properties();
            prop.load(JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties"));

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(prop.getProperty("db.url"));
            //config.setJdbcUrl("jdbc:mysql://localhost:3306/wanted_lms");
            config.setUsername(prop.getProperty("db.username"));
            config.setPassword(prop.getProperty("db.password"));
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(30000);
            config.setMaxLifetime(1800000);
            config.setConnectionTimeout(2000);

            ds = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void close() {
        if (ds != null) {
            ds.close();
        }
    }

    public static void printConnectionPoolStatus() {
        HikariPoolMXBean poolMXBean = ds.getHikariPoolMXBean();
        System.out.println("db상태");
        System.out.println(
                "커넥션 갯수 : " + poolMXBean.getActiveConnections() + "\n" + poolMXBean.getTotalConnections() + "\n"
                        + poolMXBean.getIdleConnections() + "\n" + poolMXBean.getThreadsAwaitingConnection());
    }
}
