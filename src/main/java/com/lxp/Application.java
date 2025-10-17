package com.lxp;

import com.lxp.config.JDBCConnection;
import com.lxp.controller.UserController;
import com.lxp.model.Course;
import com.lxp.model.CourseLevel;
import com.lxp.service.CourseService;
import com.lxp.service.CourseServiceImpl;
import com.lxp.util.SignInUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        try {
            Connection conn = JDBCConnection.getConnection();

            Scanner sc = new Scanner(System.in);
            while(true) {
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
                switch(num) {
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

                if(num == 1) {
                    runUserFeature(conn);
                } else {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n" + e.getStackTrace()[0]);
        }
    }

    /**
     * 사용자 관련 업무
     * @param conn
     */
    public static void runUserFeature(Connection conn) {
        UserController userController = new UserController(conn);
        Scanner sc = new Scanner(System.in);

        while(true) {
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

            switch(cmd) {
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
     * @param conn
     */
    public static void runCategoryFeature(Connection conn) {
        //TODO 필요한거 있으면 넣기(Controller, Service 선언 등)
        Scanner sc = new Scanner(System.in);

        while(true) {
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
     * @param conn
     */
    public static void runCourseFeature(Connection conn) {
        //TODO 필요한거 있으면 넣기(Controller, Service 선언 등)
        Scanner sc = new Scanner(System.in);
        CourseService courseService = new CourseServiceImpl(conn);


        while(true) {
            System.out.println();
            System.out.println("===================================");
            System.out.println("==  [강의 시스템] - 강좌 관련 업무   ==");
            System.out.println("===================================");
            System.out.println("== 1. 강좌 조회                   ==");

            // if(로그인 사용자 && 강사)
            System.out.println("== 2. 강좌 생성                   ==");
            System.out.println("== 3. 강좌 수정                   ==");
            System.out.println("== 4. 강좌 삭제                   ==");
            //
            System.out.println("== 5. 이전으로 돌아가기            ==");
            System.out.println("===================================");
            int cmd = sc.nextInt();
            sc.nextLine();

            switch (cmd) {
                case 1 -> { // 강좌 조회
                    List<Course> list =  courseService.courseFindAll();
                    list.forEach(item -> System.out.println(item.toString()));

                }
                case 2 -> { // 강좌 생성
                    System.out.print("카테고리ID : ");
                    Long categoryId = sc.nextLong();
                    sc.nextLine();

                    System.out.print("강좌 제목 : ");
                    String title = sc.nextLine();

                    System.out.print("강좌 설명 : ");
                    String description = sc.nextLine();

                    System.out.print("강좌 가격 : ");
                    int price = sc.nextInt();
                    sc.nextLine();

                    System.out.print("강좌 난이도(BEGINNER, intermediate, advanced ) : ");
                    String level = sc.nextLine();

                    Course newCourse = Course.createCourse(
                            1L, categoryId, title, description,
                            BigDecimal.valueOf(price), CourseLevel.valueOf(level)
                    );

                    Long savedId = courseService.courseSave(newCourse);
                    System.out.println("   ✅ 생성 완료: ID=" + savedId + "\n");
                }
                case 3 -> {
                    System.out.print("수정 강좌ID : ");
                    Long updateId = (long) sc.nextInt();
                    sc.nextLine();

                    Course updateCourse = courseService.findByCourseId(updateId);
                    System.out.println("[수정 대상 강좌 정보]");
                    System.out.println(updateCourse.toString() + "\n");

                    System.out.print("강좌 제목 : ");
                    updateCourse.setTitle(sc.nextLine());
                    System.out.print("강좌 설명 : ");
                    updateCourse.setDescription(sc.nextLine());
                    Long updateResult = courseService.courseUpdateByCourseId(updateCourse);

                    System.out.println("✅ 수정 완료: ID=" + updateId + "\n");

                }
                case 4 -> { // 강좌 논리 삭제
                    System.out.print("삭제 강좌ID : ");
                    int deleteId = sc.nextInt();
                    Long deleteResult = courseService.softDeleteCourseByCourseId((long) deleteId);
                    System.out.print(deleteResult + " 강좌가 삭제되었습니다.");
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
     * @param conn
     */
    public static void runSectionFeature(Connection conn) {
        //TODO 필요한거 있으면 넣기(Controller, Service 선언 등)
        Scanner sc = new Scanner(System.in);

        while(true) {
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
     * @param conn
     */
    public static void runLectureFeature(Connection conn) {
        //TODO 필요한거 있으면 넣기(Controller, Service 선언 등)
        Scanner sc = new Scanner(System.in);

        while(true) {
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

                }
                case 2 -> { // 강의 등록

                }
                case 3 -> { // 강의 수정

                }
                case 4 -> { // 강의 삭제

                }
                case 5 -> { // 강의 리소스 페이지

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
     * @param conn
     */
    public static void runLectureResourceFeature(Connection conn) {
        //TODO 필요한거 있으면 넣기(Controller, Service 선언 등)
        Scanner sc = new Scanner(System.in);

        while(true) {
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

                }
                case 2 -> { // 리소스 등록

                }
                case 3 -> { // 리소스 수정

                }
                case 4 -> { // 리소스 삭제

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
