package com.lxp.service;

import com.lxp.model.Course;
import java.util.List;

public interface CourseService {

    Boolean courseExists(long courseId);

    List<Course> courseFindAll();

    Long courseSave(Course course);

    Long courseUpdateByCourseId(Course course) ;

    Long courseDeleteByCourseId(int course_id) ;
}
