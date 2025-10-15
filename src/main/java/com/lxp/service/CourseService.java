package com.lxp.service;

import com.lxp.dao.CourseDao;

import java.sql.Connection;
import java.sql.SQLException;

public class CourseService {
    private final CourseDao courseDao;
    private final Connection connection;

    public CourseService(Connection connection) {
        this.connection = connection;
        this.courseDao = new CourseDao(connection);
    }

    public boolean courseExists(long courseId) throws SQLException {
        return courseDao.existById(courseId);
    }
}
