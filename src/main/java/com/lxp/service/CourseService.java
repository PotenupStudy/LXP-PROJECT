package com.lxp.service;

import com.lxp.model.Course;
import java.util.List;

public interface CourseService {

    public List<Course> courseFindAll();

    // 과정 등록
    public Long courseSave(Course course);

    public Long courseUpdateByCourseId(Course course) ;

    public Long courseDeleteByCourseId(Course course) ;
}
