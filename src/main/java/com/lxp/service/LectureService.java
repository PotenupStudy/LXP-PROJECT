package com.lxp.service;

import com.lxp.dao.LectureDAO;
import com.lxp.model.Lecture;
import com.lxp.model.LectureResource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LectureService {

    private final LectureDAO lectureDAO;
    private final Connection connection;

    public LectureService(Connection connection) {
        this.connection = connection;
        this.lectureDAO = new LectureDAO(connection);
    }

    public List<Lecture> findAllLectures(long userInputSectionId) throws Exception {
        List<Lecture> lectures = new ArrayList<>();
        try {
            lectures = lectureDAO.findAll(userInputSectionId);
            if (lectures.isEmpty()) {
                System.out.println("0 이상의 값을 입력하시오");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lectures;
    }

    public long createLecture(Lecture lecture) throws Exception {
        try {
            long result = lectureDAO.create(lecture);
            if (result <= 0) {
                System.out.println("강의 등록 실패");
                return 0;
            } else {
                return result;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public long updateLecture(Lecture lecture) throws Exception {
        try {
            long result = lectureDAO.update(lecture);
            if (result <= 0) {
                System.out.println("유효한 ID를 입력해주세요.");
                return 0;
            } else  {
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public long deleteLecture(Lecture lecture) throws Exception {
        long result = 0;
        try {
            result = lectureDAO.delete(lecture);
            if (result > 0) {
                return result;
            }
        } catch (RuntimeException e) {
            System.out.println("강의 삭제 실패");
        }
        return result;
    }

    public List<LectureResource> findAllResources(long userInputLectureId) throws Exception {
        List<LectureResource> resources = new ArrayList<>();
        try {
            resources = lectureDAO.findAllLr(userInputLectureId);
            if(resources.isEmpty()) {
                System.out.println("해당 Lecture ID에 등록된 강의 리소스가 존재하지 않습니다.");
            }
        } catch (IllegalArgumentException e) {
            // Enum 매핑 실패 (fromDb에서 던짐) → 원인 파악에 도움
            System.err.println("Enum 매핑 실패: " + e.getMessage());
        }
        return resources;
    }

    public long createResource(LectureResource resource) throws Exception {
        try {
            long result = lectureDAO.createLr(resource);
            if (result <= 0) {
                return 0;
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateResource(LectureResource resource) throws Exception {
        try {
            long result = lectureDAO.updateLr(resource);
            if (result <= 0) {
                System.out.println("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteResource(long resourceId) throws Exception {
        try {
            long result = lectureDAO.deleteLr(resourceId);
            if (result <= 0) {
                System.out.println("리소스 삭제 중 오류가 발생했습니다. 삭제가 완료되지 않았습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
