package com.lxp;

import com.lxp.config.JDBCConnection;
import com.lxp.controller.UserController;
import com.lxp.model.Lecture;
import com.lxp.model.LectureResource;
import com.lxp.model.ResourceType;
import com.lxp.service.LectureService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        try {
            Connection conn = JDBCConnection.getConnection();

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("===================================");
                System.out.println("==           강의 시스템           ==");
                System.out.println("===================================");
                System.out.println("= 1. 회원 관련 업무                 =");
                System.out.println("= 2. 카테고리 관련 업무              =");
                System.out.println("= 3. 강좌 관련 업무                 =");
                System.out.println("= 4. 섹션 관련 업무                 =");
                System.out.println("= 5. 강의 관련 업무                 =");
                System.out.println("= 6. 강의 리소스 관련 업무           =");
                System.out.println("= 7. 종료                          =");
                System.out.println("===================================");

                int num = sc.nextInt();
                switch (num) {
                    case 1 -> runUserFeature(conn);
                    case 2 -> runCategoryFeature(conn);
                    case 3 -> runCourseFeature(conn);
                    case 4 -> runSectionFeature(conn);
                    case 5 -> runLectureFeature(conn);
                    case 6 -> runLectureResourceFeature(conn);
                    case 7 -> {
                        break;
                    }
                    default -> {
                        System.out.println("잘못된 입력 입니다.");
                        continue;
                    }
                }

                if (num == 1) {
                    runUserFeature(conn);
                } else {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n" + e.getStackTrace()[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 사용자 관련 업무
     *
     * @param conn
     */
    public static void runUserFeature(Connection conn) {
        UserController userController = new UserController(conn);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("===================================");
            System.out.println("==   [강의 시스템] - 회원 관련 업무  ==");
            System.out.println("===================================");
            System.out.println("== 1. 회원 가입                   ==");
            System.out.println("== 2. 로그인                      ==");
            System.out.println("== 3. 로그아웃                    ==");
            System.out.println("== 4. 회원 정보 조회               ==");
            System.out.println("== 5. 회원 정보 수정               ==");
            System.out.println("== 6. 회원 탈퇴                   ==");
            System.out.println("== 7. 이전으로 돌아가기             ==");
            System.out.println("===================================");
            int cmd = sc.nextInt();
            sc.nextLine();

            switch (cmd) {
                case 1 -> { // 회원 가입

                }
                case 2 -> { // 로그인

                }
                case 3 -> { // 로그아웃

                }
                case 4 -> { // 회원 정보 조회

                }
                case 5 -> { // 회원 정보 수정

                }
                case 6 -> { // 회원 탈퇴

                }
                case 7 -> {
                    return;
                }
                default -> {
                    System.out.println("잘못된 입력 입니다.");
                }
            }
        }
    }

    /**
     * 카테고리 관련 업무
     *
     * @param conn
     */
    public static void runCategoryFeature(Connection conn) {
        //TODO 필요한거 있으면 넣기(Controller, Service 선언 등)
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("===================================");
            System.out.println("== [강의 시스템] - 카테고리 관련 업무 ==");
            System.out.println("===================================");
            System.out.println("== 1. 카테고리 전체 조회            ==");
            System.out.println("== 2. 카테고리 추가                ==");
            System.out.println("== 3. 카테고리 삭제                ==");
            System.out.println("== 4. 카테고리 수정                ==");
            System.out.println("== 5. 카테고리 인덱스 번호로 선택    ==");
            System.out.println("== 6. 이전으로 돌아가기             ==");
            System.out.println("===================================");
            int cmd = sc.nextInt();
            sc.nextLine();

            switch (cmd) {
                case 1 -> { // 카테고리 전체 조회

                }
                case 2 -> { // 카테고리 추가

                }
                case 3 -> { // 카테고리 삭제

                }
                case 4 -> { // 카테고리 수정

                }
                case 5 -> { // 카테고리 인덱스 번호로 선택

                }
                case 6 -> { // 이전으로 돌아가기
                    return;
                }
                default -> {
                    System.out.println("잘못된 입력 입니다.");
                }
            }
        }
    }

    /**
     * 강좌 관련 업무
     *
     * @param conn
     */
    public static void runCourseFeature(Connection conn) {
        //TODO 필요한거 있으면 넣기(Controller, Service 선언 등)
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("===================================");
            System.out.println("==  [강의 시스템] - 강좌 관련 업무   ==");
            System.out.println("===================================");
            System.out.println("== 1. 강좌 조회                   ==");
            System.out.println("== 2. 강좌 생성                   ==");
            System.out.println("== 3. 강좌 수정                   ==");
            System.out.println("== 4. 강좌 삭제                   ==");
            System.out.println("== 5. 이전으로 돌아가기            ==");
            System.out.println("===================================");
            int cmd = sc.nextInt();
            sc.nextLine();

            switch (cmd) {
                case 1 -> { // 강좌 조회

                }
                case 2 -> { // 강좌 생성

                }
                case 3 -> { // 강좌 수정

                }
                case 4 -> { // 강좌 삭제

                }
                case 5 -> { // 이전으로 돌아가기
                    return;
                }
                default -> {
                    System.out.println("잘못된 입력 입니다.");
                }
            }
        }
    }

    /**
     * 섹션 관련 업무
     *
     * @param conn
     */
    public static void runSectionFeature(Connection conn) {
        //TODO 필요한거 있으면 넣기(Controller, Service 선언 등)
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("===================================");
            System.out.println("==  [강의 시스템] - 섹션 관련 업무   ==");
            System.out.println("===================================");
            System.out.println("== 1. 섹션 생성                   ==");
            System.out.println("== 2. 섹션 조회 (ID)              ==");
            System.out.println("== 3. 강좌별 섹션 목록 조회         ==");
            System.out.println("== 4. 섹션 수정                   ==");
            System.out.println("== 5. 섹션 삭제                   ==");
            System.out.println("== 6. 이전으로 돌아가기            ==");
            System.out.println("===================================");
            int cmd = sc.nextInt();
            sc.nextLine();

            switch (cmd) {
                case 1 -> { // 섹션 생성

                }
                case 2 -> { // 섹션 조회 (ID)

                }
                case 3 -> { // 강좌별 섹션 목록 조회

                }
                case 4 -> { // 섹션 수정

                }
                case 5 -> { // 섹션 삭제

                }
                case 6 -> { // 이전으로 돌아가기
                    return;
                }
                default -> {
                    System.out.println("잘못된 입력 입니다.");
                }
            }
        }
    }

    /**
     * 강의 관련 업무
     *
     * @param conn
     */
    public static void runLectureFeature(Connection conn) throws Exception {
        LectureService lectureService = new LectureService(conn);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("===================================");
            System.out.println("==  [강의 시스템] - 강의 관련 업무   ==");
            System.out.println("===================================");
            System.out.println("== 1. 강의 조회                   ==");
            System.out.println("== 2. 강의 등록                   ==");
            System.out.println("== 3. 강의 수정                   ==");
            System.out.println("== 4. 강의 삭제                   ==");
            System.out.println("== 5. 강의 리소스 페이지           ==");
            System.out.println("== 6. 이전으로 돌아가기            ==");
            System.out.println("===================================");
            int cmd = sc.nextInt();
            sc.nextLine();

            switch (cmd) {
                case 1 -> { // 강의 조회
                    System.out.println("조회할 강의의 섹션ID를 입력해주세요.");
                    long sectionId = sc.nextLong();
                    sc.nextLine();

                    if (sectionId <= 0) {
                        System.out.println("1 이상의 정수로 입력해주세요.");
                        continue;
                    }
                    List<Lecture> lectures = lectureService.findAllLectures(sectionId);
                    if (lectures.isEmpty()) {
                        System.out.println("해당 섹션에 강의가 존재하지 않습니다.");
                        continue;
                    }
                    for (Lecture lecture : lectures) {
                        System.out.println(lecture);
                    }
                }
                case 2 -> { // 강의 등록
                    System.out.println("등록할 강의의 섹션ID를 입력해주세요.");
                    long sectionId = sc.nextLong();
                    sc.nextLine();

                    try {
                        LectureService.Validators.requireId(sectionId, "섹션 ID");

                        System.out.println("등록할 강의의 제목을 입력해주세요.");
                        String title = sc.nextLine();
                        LectureService.Validators.requireLength(title, 200, "강의 제목");

                        System.out.println("등록할 강의의 설명을 입력해주세요.");
                        String description = sc.nextLine();

                        System.out.println("등록할 강의의 순서를 입력해주세요.");
                        int order = sc.nextInt();
                        sc.nextLine();
                        if (order <= 0) throw new IllegalArgumentException("순서는 1 이상의 정수여야 합니다.");

                        Lecture lecture = new Lecture(sectionId, title, description, order);
                        lectureService.createLecture(lecture);
                        System.out.println("강의 등록 완료!");

                    } catch (IllegalArgumentException e) {
                        System.out.println("[입력 오류] " + e.getMessage());
                    } catch (RuntimeException e) {
                        System.out.println("[처리 실패] " + e.getMessage());
                    }
                }

                case 3 -> { // 강의 수정
                    System.out.println("수정할 강의의 섹션ID를 입력해주세요.");
                    long section_id = sc.nextLong();
                    sc.nextLine();

                    List<Lecture> lectures = lectureService.findAllLectures(section_id);
                    if (lectures.isEmpty()) {
                        System.out.println("해당 섹션에 강의가 존재하지 않습니다.");
                        continue;
                    }
                    if (section_id <= 0) {
                        System.out.println("1 이상의 정수로 입력해주세요.");
                        continue;
                    }
                    for (Lecture lecture : lectures) {
                        System.out.println(lecture);
                    }

                    System.out.println("수정할 강의의 Lecture ID를 입력해주세요.");
                    long lecture_id = sc.nextLong();
                    sc.nextLine();

                    try {
                        LectureService.Validators.requireId(lecture_id, "렉쳐 ID");

                        System.out.println("등록할 강의의 제목을 입력해주세요.");
                        String title = sc.nextLine();
                        LectureService.Validators.requireLength(title, 200, "강의 제목");

                        System.out.println("등록할 강의의 설명을 입력해주세요.");
                        String description = sc.nextLine();

                        System.out.println("등록할 강의의 순서를 입력해주세요.");
                        int order = sc.nextInt();
                        sc.nextLine();
                        if (order <= 0) throw new IllegalArgumentException("순서는 1 이상의 정수여야 합니다.");

                        Lecture lecture = new Lecture(description, order, title, section_id, lecture_id);
                        lectureService.updateLecture(lecture);
                        System.out.println("강의 수정 완료!");

                    } catch (IllegalArgumentException e) {
                        System.out.println("[입력 오류] " + e.getMessage());
                    } catch (RuntimeException e) {
                        System.out.println("[처리 실패] " + e.getMessage());
                    }
                }

                case 4 -> { // 강의 삭제
                    System.out.println("삭제할 강의의 섹션ID를 입력해주세요.");
                    long section_id = sc.nextInt();
                    sc.nextLine();

                    List<Lecture> lectures = lectureService.findAllLectures(section_id);
                    if (lectures.isEmpty()) {
                        System.out.println("해당 섹션에 강의가 존재하지 않습니다.");
                        continue;
                    }
                    if (section_id <= 0) {
                        System.out.println("1 이상의 정수로 입력해주세요.");
                        continue;
                    }
                    for (Lecture lecture : lectures) {
                        System.out.println(lecture);
                    }
                    System.out.println("삭제할 강의의 Lecture ID를 입력해주세요.");
                    long lecture_id = sc.nextInt();
                    sc.nextLine();

                    if (lecture_id <= 0) {
                        System.out.println("1 이상의 정수로 입력해주세요");
                        continue;
                    }
                    System.out.println("선택된 Lecture ID : " + lecture_id + " 정말 삭제하시겠습니까? 1:예 / 2:아니오");
                    int choice = sc.nextInt();
                    sc.nextLine();

                    if (choice == 1) {
                        Lecture lecture = new Lecture(section_id, lecture_id);
                        lectureService.deleteLecture(lecture);
                        System.out.println("강의 삭제 완료!");

                    } else if (choice == 2) {
                        System.out.println("삭제를 취소합니다.");
                    }
                    System.out.println("잘못된 번호입니다. 이전으로 돌아갑니다.");
                    return;
                }
                case 5 -> { // 강의 리소스 페이지
                    runLectureResourceFeature(conn);
                }
                case 6 -> { // 이전으로 돌아가기
                    return;
                }
                default -> {
                    System.out.println("잘못된 입력 입니다.");
                }
            }
        }
    }

    /**
     * 강의 리소스 관련 업무
     *
     * @param conn
     */
    public static void runLectureResourceFeature(Connection conn) throws Exception {
        LectureService lectureService = new LectureService(conn);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("===================================");
            System.out.println("== [강의 시스템] - 강의 리소스 업무  ==");
            System.out.println("===================================");
            System.out.println("== 1. 리소스 조회                 ==");
            System.out.println("== 2. 리소스 등록                 ==");
            System.out.println("== 3. 리소스 수정                 ==");
            System.out.println("== 4. 리소스 삭제                 ==");
            System.out.println("== 5. 이전으로 돌아가기            ==");
            System.out.println("===================================");
            int cmd = sc.nextInt();
            sc.nextLine();

            switch (cmd) {
                case 1 -> { // 리소스 조회
                    System.out.println("조회할 강의의 Lecture ID를 입력해주세요.");
                    long lectureId = sc.nextLong();
                    sc.nextLine();

                    if (lectureId <= 0) {
                        System.out.println("1 이상의 정수를 입력해주세요.");
                        continue;
                    }
                    for (LectureResource rs : lectureService.findAllResources(lectureId)) {
                        System.out.println(rs);
                    }
                }

                case 2 -> { // 리소스 등록
                    System.out.println("등록할 강의 리소스의 Lecture ID를 입력해주세요.");
                    long lectureId = sc.nextLong();
                    sc.nextLine();

                    try {
                        LectureService.Validators.requireId(lectureId, "Lecture ID");

                        System.out.println("등록할 강의 리소스의 제목을 입력해주세요.");
                        String resourceName = sc.nextLine();
                        LectureService.Validators.requireLength(resourceName, 50, "리소스 제목");

                        System.out.println("등록할 강의 리소스의 타입을 입력해주세요. (url || vod || file)");
                        String typeStr = sc.nextLine();
                        ResourceType resourceType = ResourceType.fromInput(typeStr); // 리소스 타입 검증

                        System.out.println("등록할 강의 리소스의 URL을 입력해주세요.");
                        String resourceUrl = sc.nextLine();
                        LectureService.Validators.requireUrl(resourceUrl); // ️URL 검증

                        System.out.println("등록할 강의 리소스의 순서를 입력해주세요.");
                        int resourceOrder = sc.nextInt();
                        sc.nextLine();
                        if (resourceOrder <= 0) throw new IllegalArgumentException("순서는 1 이상의 정수여야 합니다.");

                        System.out.println("등록할 강의 리소스의 영상 길이(초). * vod가 아니면 0");
                        int resourceDuration = sc.nextInt();
                        sc.nextLine();
                        LectureService.Validators.requireResourceDuration(resourceType, resourceDuration); // 영상 길이 규칙

                        LectureResource resource = new LectureResource(
                                lectureId, resourceName, resourceType, resourceUrl, resourceOrder, resourceDuration
                        );
                        lectureService.createResource(resource);
                        System.out.println("리소스 등록 완료!");

                    } catch (IllegalArgumentException e) {
                        System.out.println("[입력 오류] " + e.getMessage());
                    } catch (RuntimeException e) {
                        System.out.println("[처리 실패] " + e.getMessage());
                    }
                }

                case 3 -> { // 리소스 수정
                    System.out.println("수정할 리소스의 Lecture ID를 입력해주세요.");
                    long lectureId = sc.nextLong();
                    sc.nextLine();

                    for (LectureResource rs : lectureService.findAllResources(lectureId)) {
                        System.out.println(rs);
                    }

                    System.out.println("수정할 리소스의 Resource ID를 입력해주세요.");
                    long resourceId = sc.nextLong();
                    sc.nextLine();

                    try {
                        LectureService.Validators.requireId(lectureId, "Lecture ID");
                        LectureService.Validators.requireId(resourceId, "Resource ID");

                        System.out.println("리소스 제목을 입력해 주세요.");
                        String resourceName = sc.nextLine();
                        LectureService.Validators.requireLength(resourceName, 50, "리소스 제목");

                        System.out.println("리소스 타입을 입력해주세요. (url|vod|file)");
                        String typeStr = sc.nextLine();
                        ResourceType resourceType = ResourceType.fromInput(typeStr);

                        System.out.println("리소스 url을 입력해주세요.");
                        String resourceUrl = sc.nextLine();
                        LectureService.Validators.requireUrl(resourceUrl);

                        System.out.println("리소스 순서를 입력해주세요.");
                        int resourceOrder = sc.nextInt();
                        sc.nextLine();
                        if (resourceOrder <= 0) throw new IllegalArgumentException("순서는 1 이상의 정수여야 합니다.");

                        System.out.println("리소스가 vod라면 영상 길이를 입력해주세요. 아니라면 0을 입력해주세요.");
                        int resourceDuration = sc.nextInt();
                        sc.nextLine();
                        LectureService.Validators.requireResourceDuration(resourceType, resourceDuration);

                        LectureResource resource = new LectureResource(
                                resourceName, resourceType, resourceUrl, resourceOrder, resourceDuration, resourceId, lectureId
                        );
                        lectureService.updateResource(resource);
                        System.out.println("리소스 수정 완료!");

                    } catch (IllegalArgumentException e) {
                        System.out.println("[입력 오류] " + e.getMessage());
                    } catch (RuntimeException e) {
                        System.out.println("[처리 실패] " + e.getMessage());
                    }
                }

                case 4 -> { // 리소스 삭제
                    System.out.println("삭제할 리소스의 resourceID를 입력하세요.");
                    long resourceId = sc.nextLong();
                    sc.nextLine();

                    if (resourceId <= 0) {
                        System.out.println("1 이상의 정수를 입력해 주세요.");
                        continue;
                    }

                    System.out.println("선택된 Resource ID : " + resourceId + " 정말 삭제하시겠습니까? 1:예 / 2:아니오");
                    int choice = sc.nextInt();

                    if (choice == 1) {
                        lectureService.deleteResource(resourceId);
                        System.out.println("삭제 성공! 삭제된 Resource ID : " + resourceId);

                    } else if (choice == 2) {
                        System.out.println("삭제를 취소합니다. 이전으로 돌아갑니다.");
                        return;
                    }
                    System.out.println("잘못된 번호입니다. 이전 메뉴로 돌아갑니다");
                    return;
                }
                case 5 -> { // 이전으로 돌아가기
                    return;
                }
                default -> {
                    System.out.println("잘못된 입력 입니다.");

                }
            }
        }
    }
}

