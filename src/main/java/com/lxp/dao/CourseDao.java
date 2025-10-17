package com.lxp.dao;

import com.lxp.model.Course;
import com.lxp.model.CourseLevel;
import com.lxp.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    private final Connection connection;

    public CourseDao(Connection connection) {
        this.connection = connection;
    }

    public boolean existById(long courseId) {
        String query = QueryUtil.getQuery("course.courseFindExistById");

        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("row_count");
            }

        } catch (SQLException e) {
            throw new RuntimeException("[error][ " + "CourseDao" + "." + "delete" + "] "
                    + e.getMessage());
        }

        return false;
    }

    public List<Course> findAll(){
        List<Course> courses = new ArrayList<>();
        String sql = QueryUtil.getQuery("course.findAll");

        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            ResultSet result = pstmt.executeQuery();

            while (result.next()){
                Course course = new Course(
                        result.getInt("course_id"),
                        result.getInt("user_id"),
                        result.getInt("category_id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getBigDecimal("price"),
                        CourseLevel.from(result.getString("course_level"))
                );

                courses.add(course);

            }
        } catch (SQLException e) {
            throw new RuntimeException("[error][ " + "CourseDao" + "." + "findAll" + "] "
                    + e.getMessage());
        }

        return courses;
    }

    public long save(Course course) {
        String sql = QueryUtil.getQuery("course.save");
        int result = 0;

        try(PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setInt(1, course.getUserId());
            pstmt.setInt(2, course.getCategoryId());
            pstmt.setString(3, course.getTitle());
            pstmt.setString(4, course.getDescription());
            pstmt.setBigDecimal(5, course.getPrice());
            pstmt.setString(6, course.getCourseLevel().getValue());

            result = pstmt.executeUpdate();

            if(result > 0)
                try (ResultSet re = pstmt.getGeneratedKeys()){
                    if(re.next())
                        return re.getInt(1);

                }

        } catch (SQLException e) {
            throw new RuntimeException("[error][ " + "CourseDao" + "." + "save" + "] "
                    + e.getMessage());
        }

        return result;
    }

    public Long update(Course updateCourse) {
        String query = QueryUtil.getQuery("course.update");

        int rowsAffected = 0;

        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, updateCourse.getCategoryId());
            pstmt.setString(2, updateCourse.getTitle());
            pstmt.setString(3, updateCourse.getDescription());
            pstmt.setBigDecimal(4, updateCourse.getPrice());
            pstmt.setString(5, updateCourse.getCourseLevel().getValue());
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(7, updateCourse.getCourseId());

            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[error][ " + "CourseDao" + "." + "update" + "] "
                    + e.getMessage());
        }

        return (long) rowsAffected;
    }

    public Long delete(int courseId) {
        String query = QueryUtil.getQuery("course.delete");

        int rowsAffected = 0;

        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, courseId);

            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[error][ " + "CourseDao" + "." + "delete" + "] "
                    + e.getMessage());
        }

        return (long) rowsAffected;
    }


}
