package com.lxp.service;

import com.lxp.dao.CourseDao;
import com.lxp.model.Course;

import java.sql.Connection;
import java.util.List;

public class CourseServiceImpl implements CourseService {

    private final CourseDao courseDao;
    private final Connection connection;

    public CourseServiceImpl(Connection connection){
        this.connection = connection;
        this.courseDao = new CourseDao(connection);
    }

    @Override
    public Boolean courseExists(Long courseId) {
        return courseDao.existById(courseId);
    }


    @Override
    public List<Course> courseFindAll(){
        return courseDao.findAll();
    }

    @Override
    public List<Course> courseFindByCategoryId(Long categoryId) {
        return courseDao.findByCategoryId(categoryId);
    }

    // 과정 등록
    @Override
    public Long courseSave(Course course) {
        Long result = null;

        try{
            result = courseDao.save(course);
            if(result < 0){
                System.out.println("log : 과정등록 실패");
                return null;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log : error");
        }

        return result;
    }

    @Override
    public Long courseUpdateByCourseId(Course course) {
        Long result = null;
        try{
            course.validateForUpdate();
            return courseDao.update(course);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log : error");
        }

        return result;
    }


    @Override
    public Long softDeleteCourseByCourseId(Long course_id) {
        Long result = null;
        try{
            if(course_id < 0)
                throw new IllegalArgumentException("삭제");
            result = courseDao.softDelete(course_id);

            if(result < 0){
                System.out.println("log : 강좌삭제 실패");
                return null;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log : error");
        }

        return result;
    }

    @Override
    public Long courseDeleteByCourseId(Long course_id) {
        Long result = null;
        try{
            if(course_id < 0)
                throw new IllegalArgumentException("삭제");
            result = courseDao.delete(course_id);

            if(result < 0){
                System.out.println("log : 강좌삭제 실패");
                return null;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log : error");
        }

        return result;
    }
}
