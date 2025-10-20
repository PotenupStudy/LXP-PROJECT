package com.lxp.controller;

import com.lxp.model.Course;
import com.lxp.model.CourseLevel;
import com.lxp.model.Role;
import com.lxp.service.*;
import com.lxp.util.SignInUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class CourseController {

    Scanner sc = new Scanner(System.in);
    CourseService courseService;

    public CourseController(Connection connection) {
        this.courseService = new CourseServiceImpl(connection);
    }

    public void runCourseFeature(Connection conn) {

        while (true) {
            System.out.println();
            System.out.println("===================================");
            System.out.println("==  [강의 시스템] - 강좌 관련 업무   ==");
            System.out.println("===================================");
            System.out.println("== 1. 강좌 조회                   ==");

            if(SignInUtil.role != null && SignInUtil.role.equals(Role.INSTRUCTOR)){
                System.out.println("== 2. 강좌 생성                   ==");
                System.out.println("== 3. 강좌 수정                   ==");
                System.out.println("== 4. 강좌 삭제                   ==");
            }

            System.out.println("== 5. 이전으로 돌아가기            ==");
            System.out.println("===================================");
            int cmd = sc.nextInt();
            sc.nextLine();

            switch (cmd) {
                case 1 -> findAllCourseList(); //강좌 조회
                case 2 -> createCourse(); // 강좌 생성
                case 3 -> updateCourse(); // 강좌 수정
                case 4 -> deleteCourse(); // 강좌 삭제
                case 5 -> { // 이전으로 돌아가기
                    return;
                }
                default -> {
                    System.out.println("잘못된 입력 입니다.");
                }
            }
        }
    }


    void findAllCourseList(){
        List<Course> list =  courseService.courseFindAll();
        list.forEach(item -> System.out.println(item.toString()));
    }

    void createCourse(){
        if(!SignInUtil.role.equals(Role.INSTRUCTOR)){
            System.out.println("강사만 강좌를 개설할 수 있습니다.");
            return;
        }

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

        System.out.print("강좌 난이도(BEGINNER, INTERMEDIATE, ADVANCED ) : ");
        String level = sc.nextLine();

        Course newCourse = Course.createCourse(
                SignInUtil.userId, categoryId, title, description,
                BigDecimal.valueOf(price), CourseLevel.valueOf(level)
        );

        Long savedId = courseService.courseSave(newCourse);
        System.out.println("   ✅ 생성 완료: ID=" + savedId + "\n");

    }

    void updateCourse(){
        System.out.print("수정 강좌ID : ");
        Long updateId = (long) sc.nextInt();
        sc.nextLine();

        Course updateCourse = courseService.findByCourseId(updateId);
        System.out.println("[수정 대상 강좌 정보]");
        System.out.println(updateCourse.toString() + "\n");

        if(SignInUtil.userId != updateCourse.getUserId()){
            System.out.println("강좌를 개설한 강사만 수정할 수 있습니다.");
            return;
        }

        System.out.print("강좌 제목 : ");
        updateCourse.setTitle(sc.nextLine());
        System.out.print("강좌 설명 : ");
        updateCourse.setDescription(sc.nextLine());
        Long updateResult = courseService.courseUpdateByCourseId(updateCourse);

        System.out.println("✅ 수정 완료: ID=" + updateId + "\n");

    }

    void deleteCourse(){ // 강좌 논리 삭제
        System.out.print("삭제 강좌ID : ");
        long deleteId = sc.nextInt();
        Course deleteCourse = courseService.findByCourseId(deleteId);
        System.out.println("[삭제 대상 강좌 정보]");
        System.out.println(deleteCourse.toString() + "\n");

        if(SignInUtil.userId != deleteCourse.getUserId()){
            System.out.println("강좌를 개설한 강사만 삭제할 수 있습니다.");
            return;
        }

        Long deleteResult = courseService.softDeleteByCourseId(deleteId);

        System.out.print(deleteResult + " 강좌가 삭제되었습니다.");
    }

}
