package com.lxp.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

public class TransactionManager {

    public TransactionManager() {
    }

    public <T> T execute(Function<Connection, T> work) {
        try (Connection con = JDBCConnection.getConnection()) {
            boolean prev = con.getAutoCommit();
            con.setAutoCommit(false);
            try {
                T result = work.apply(con);
                con.commit();
                return result;
            } catch (RuntimeException | Error e) {
                try { con.rollback(); } catch (SQLException ignore) {}
                throw e;
            } finally {
                try { con.setAutoCommit(prev); } catch (SQLException ignore) {}
            }
        } catch (SQLException e) {
            throw new RuntimeException("트랜잭션 시작 실패", e);
        }
    }

}
