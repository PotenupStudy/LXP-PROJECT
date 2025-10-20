package com.lxp.service;

import com.lxp.config.TransactionManager;
import com.lxp.dao.CourseDao;
import com.lxp.model.Course;
import com.lxp.model.Lecture;
import com.lxp.model.dto.ViewSectionDto;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CourseServiceImpl implements CourseService {

    private final CourseDao courseDao;
    private final Connection connection;
    SectionService sectionService;
    LectureService lectureService;

    public CourseServiceImpl(Connection connection){
        this.connection = connection;
        this.courseDao = new CourseDao(connection);
        this.sectionService = new SectionServiceImpl(connection);
        this.lectureService = new LectureService(connection);
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

    @Override
    public Course findByCourseId(Long courseId) {
        return courseDao.findByCourseId(courseId);
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
    public Long softDeleteByCourseId(Long course_id) {
        return new TransactionManager().execute(connection -> {
            Long deleteId = null;
            try{
                List<ViewSectionDto> sectionList = sectionService.findSectionsByCourseId(course_id);
                List<Lecture> lectureList = new ArrayList<>();
                sectionList.forEach(section -> {
                    sectionService.deleteSection(section.getSectionId());

                    try {
                        lectureList.addAll(lectureService.findAllLectures(section.getSectionId()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });

                lectureList.forEach(lecture -> {
                    try {
                        lectureService.deleteLecture(lecture);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });


                return softDeleteCourseByCourseId(course_id);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("log : error");
            }
            return deleteId;
        });
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
