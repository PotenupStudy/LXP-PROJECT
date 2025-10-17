package com.lxp;

import com.lxp.config.JDBCConnection;
import com.lxp.controller.CategoryController;
import com.lxp.controller.UserController;
import com.lxp.model.Category;
import com.lxp.service.CategoryService;
import com.lxp.util.CategoryFactory;
import com.lxp.util.InputUtil;
import com.lxp.util.Validator;
import com.lxp.model.dto.RegisterUserDto;
import com.lxp.util.SignInUtil;

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
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n" + e.getStackTrace()[0]);
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
                    RegisterUserDto registerUserDto;

                    System.out.print("이름을 입력하세요 : ");
                    String name = sc.nextLine();
                    System.out.print("이메일을 입력하세요 : ");
                    String email = sc.nextLine();
                    System.out.print("역할을 입력하세요(1. 학생, 2. 교사) : ");
                    String inputRole = sc.nextLine();
                    registerUserDto = new RegisterUserDto(name, email, inputRole);
                    try {
                        long registeredId = userController.registerUser(registerUserDto);
                        System.out.println("Log[INFO] - 회원 등록 완료. 입력 값: " + registerUserDto);
                        System.out.println("회원 가입이 완료되었습니다. 가입된 번호 : " + registeredId);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Log[ERROR] - " + e.getMessage() + " / " + registerUserDto);
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> { // 로그인
                    if(SignInUtil.isSignIn) {
                        System.out.println("이미 로그인 되어 있습니다.");
                        continue;
                    }
                    System.out.print("이메일을 입력하세요 : ");
                    String email = sc.nextLine();

                    try {
                        if (userController.signInUser(email)) {
                            System.out.println("로그인 성공");
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> { // 로그아웃
                    if(!SignInUtil.isSignIn) {
                        System.out.println("로그인 되지 않았습니다.");
                        continue;
                    }

                    SignInUtil.signOut();
                }
                case 4 -> { // 회원 정보 조회
                    if(!SignInUtil.isSignIn) {
                        System.out.println("로그인 되지 않았습니다.");
                        continue;
                    }
                    System.out.println(userController.viewUser(SignInUtil.userId));
                }
                case 5 -> { // 회원 정보 수정
                    if(!SignInUtil.isSignIn) {
                        System.out.println("로그인 되지 않았습니다.");
                        continue;
                    }
                    System.out.print("변경 할 이름을 입력하세요 : ");
                    String name = sc.nextLine();
                    if(userController.editUserInfo(name, SignInUtil.userId) > 0) {
                        System.out.println("변경 완료");
                    }
                }
                case 6 -> { // 회원 탈퇴
                    if(!SignInUtil.isSignIn) {
                        System.out.println("로그인 되지 않았습니다.");
                        continue;
                    }
                    System.out.print("정말 탈퇴하시겠습니까?(Y/N) : ");
                    String yn = sc.nextLine().toLowerCase();

                    if(!yn.equals("y")) continue;

                    if(userController.withdrawalUser(SignInUtil.userId) > 0) {
                        SignInUtil.signOut();
                        System.out.println("회원 탈퇴 완료");
                    }
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
    public static void runCategoryFeature(Connection conn) throws SQLException {
        //TODO 필요한거 있으면 넣기(Controller, Service 선언 등)
        Scanner sc = new Scanner(System.in);
        CategoryFactory cf = new CategoryFactory();

        CategoryService categoryService = cf.categoryService(conn);
        CategoryController categoryController = new CategoryController(categoryService);
        while (true) {
            System.out.println();
            System.out.println("===================================");
            System.out.println("== [강의 시스템] - 카테고리 관련 업무 ==");
            System.out.println("===================================");
            System.out.println("== 1. 카테고리 전체 조회            ==");
            System.out.println("== 2. 카테고리 인덱스 번호로 선택   ==");
            System.out.println("== 3. 카테고리 추가                 ==");
            System.out.println("== 4. 카테고리 수정                 ==");
            System.out.println("== 5. 카테고리 삭제                 ==");
            System.out.println("== 6. 이전으로 돌아가기             ==");
            System.out.println("===================================");
            int cmd = InputUtil.readValidInt("번호를 선택해주세요");

            switch (cmd) {
                case 1 -> { // 카테고리 전체 조회
                    List<Category> categories = categoryController.getAllCategories();
                    if (categories.isEmpty()) {
                        System.out.println("처음으로 돌아갑니다.");
                    }

                }
                case 2 -> { // 카테고리 인덱스 번호로 선택

                    Category category = categoryController.getCategoryByIndex();

                    System.out.println("선택한 카테고리 정보 : ");
                    System.out.println("===========================================");
                    System.out.println(category.toString());
                }
                case 3 -> { // 카테고리 추가
                    String categoryName = InputUtil.readString("추가할 카테고리 이름을 입력하세요: ");
                    System.out.println("===========================================");
                    categoryController.createCategory(categoryName);
                }
                case 4 -> { // 카테고리 수정
                    System.out.println("수정할 카테고리를 선택해 주세요.");
                    System.out.println("===========================================");
                    categoryController.updateCategory();
                    System.out.println("-------------------변경후------------------");
                    categoryController.getAllCategories();
                }
                case 5 -> { // 카테고리 삭제
                    System.out.println("삭제할 카테고리를 선택해 주세요.");
                    System.out.println("===========================================");
                    categoryController.deleteCategory();
                    System.out.println("-------------------변경후------------------");
                    categoryController.getAllCategories();
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
    public static void runLectureFeature(Connection conn) {
        //TODO 필요한거 있으면 넣기(Controller, Service 선언 등)
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
     *
     * @param conn
     */
    public static void runLectureResourceFeature(Connection conn) {
        //TODO 필요한거 있으면 넣기(Controller, Service 선언 등)
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
