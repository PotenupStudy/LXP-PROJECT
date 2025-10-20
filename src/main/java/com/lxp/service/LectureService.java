package com.lxp.service;

import com.lxp.dao.LectureDAO;
import com.lxp.model.Lecture;
import com.lxp.model.LectureResource;
import com.lxp.model.ResourceType;

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
                System.out.println("1 이상의 값을 입력하시오");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lectures;
    }

    public long createLecture(Lecture lecture) throws Exception {
        if (lectureDAO.existBySectionIdAndOrderNum(lecture.getSection_id(), lecture.getLecture_order())) {
            throw new RuntimeException("강의 순서는 중복될 수 없습니다.");
        }
        return lectureDAO.create(lecture);
    }

    public long updateLecture(Lecture lecture) throws Exception {

        if (lectureDAO.existBySectionIdAndOrderNum(lecture.getSection_id(), lecture.getLecture_order())) {
            throw new RuntimeException("강의 순서는 중복될 수 없습니다.");
        } else {
            return lectureDAO.update(lecture);
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
                System.out.println("해당 Lecture ID에 등록된 강의 리소스가 존재하지 않습니다. [강의 리소스 업무] 페이지로 돌아갑니다.");
            }
        } catch (IllegalArgumentException e) {
            // Enum 매핑 실패 (fromDb에서 던짐) → 원인 파악에 도움
            System.err.println("Enum 매핑 실패: " + e.getMessage());
        }
        return resources;
    }

    public long createResource(LectureResource resource) throws Exception {
        if (lectureDAO.existByLectureIdAndOrderNumInResource(resource.getLecture_id(), resource.getOrder_index())) {
            throw new RuntimeException("리소스 순서는 중복될 수 없습니다.");
        } else {
            return lectureDAO.createLr(resource);
        }
    }

    public long updateResource(LectureResource resource) throws Exception {
        if (lectureDAO.existByLectureIdAndOrderNumInResource(resource.getLecture_id(), resource.getOrder_index())) {
            throw new RuntimeException("리소스 순서는 중복될 수 없습니다.");
        } else {
            return lectureDAO.updateLr(resource);
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

    public final class Validators {
        private Validators() {}

        public static void requireId(long id, String name) {
            if (id <= 0) throw new IllegalArgumentException("[ERROR] " + name + "는 1 이상의 정수여야 합니다. 이전으로 돌아갑니다.");
        }

        public static void requireLength(String s, int max, String name) {
            if (s == null || s.isBlank())
                throw new IllegalArgumentException("[ERROR] " + name + "은(는) 필수입니다. 이전으로 돌아갑니다.");
            if (s.length() > max)
                throw new IllegalArgumentException("[ERROR] " + name + "은(는) " + max + "자 이하여야 합니다. 이전으로 돌아갑니다.");
        }

        public static void requireUrl(String url) {
            requireLength(url, 255, "리소스 URL");
            if (!url.matches("(?i)^https?://.+"))
                throw new IllegalArgumentException("[ERROR] 리소스 URL은 http(s)로 시작해야 합니다. 이전으로 돌아갑니다.");
        }

        public static void requireResourceDuration(ResourceType type, int duration) {
            if (type == ResourceType.VOD) {
                if (duration <= 0)
                    throw new IllegalArgumentException("[ERROR] VOD의 영상 길이는 1초 이상이어야 합니다. 이전으로 돌아갑니다.");
                if (duration > 24 * 60 * 60)
                    throw new IllegalArgumentException("[ERROR] 영상 길이는 24시간을 초과할 수 없습니다. 이전으로 돌아갑니다.");
            } else {
                if (duration != 0)
                    throw new IllegalArgumentException("[ERROR] VOD가 아니면 영상 길이는 0이어야 합니다. 이전으로 돌아갑니다." );
            }
        }
    }

}
