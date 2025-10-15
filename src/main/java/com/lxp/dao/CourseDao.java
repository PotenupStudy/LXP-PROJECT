package com.lxp.dao;

import com.lxp.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDao {
    private final Connection connection;

    public CourseDao(Connection connection) {
        this.connection = connection;
    }

    public boolean existById(long courseId) throws SQLException {
        String sql = QueryUtil.getQuery("course.existById");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, courseId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1) > 0;
                }
            }
        }
        return false;
    }
}
