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
    public List<Course> courseFindAll(){
        return courseDao.findAll();
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
            result = courseDao.update(course);
            if(result < 0){
                System.out.println("log : 과정수정 실패");
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
    public Long courseDeleteByCourseId(Course course) {
        Long result = null;
        try{
            course.validateForDelete();
            result = courseDao.delete(course.getCourseId());
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
}
