package com.lxp.service;

import com.lxp.model.Course;
import java.util.List;

public interface CourseService {

    Boolean courseExists(Long courseId);

    List<Course> courseFindAll();

    List<Course> courseFindByCategoryId(Long categoryId);

    Course findByCourseId(Long courseId);

    Long courseSave(Course course);

    Long courseUpdateByCourseId(Course course) ;

    Long softDeleteCourseByCourseId(Long course_id);

    Long courseDeleteByCourseId(Long course_id) ;
}
