package com.lxp;

import com.lxp.config.JDBCConnection;
import com.lxp.controller.CategoryController;
import com.lxp.controller.UserController;
import com.lxp.model.Lecture;
import com.lxp.model.LectureResource;
import com.lxp.model.ResourceType;
import com.lxp.service.LectureService;

import com.lxp.model.dto.ViewSectionDto;
import com.lxp.service.SectionService;
import com.lxp.service.SectionServiceImpl;
import com.lxp.util.Validator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;

import com.lxp.model.Category;
import com.lxp.service.CategoryService;
import com.lxp.util.CategoryFactory;
import com.lxp.util.InputUtil;
import com.lxp.util.Validator;
import com.lxp.model.dto.RegisterUserDto;
import com.lxp.util.SignInUtil;
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
                        return;
                    }
                    default -> {
                        System.out.println("잘못된 입력 입니다.");
                    }
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
        CourseService courseService = new CourseServiceImpl(conn);


        while (true) {
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
     *
     * @param conn
     */
    public static void runSectionFeature(Connection conn) {
        // 의존성 주입: Service와 Scanner 객체 생성
        SectionService sectionService = new SectionServiceImpl(conn);
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
            System.out.print("메뉴를 선택하세요: ");

            int cmd = 0;
            try {
                cmd = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                sc.nextLine(); // 잘못된 입력 비우기
                continue; // 메뉴 선택으로 돌아가기
            }
            sc.nextLine();

            switch (cmd) {
                case 1 -> { // 섹션 생성
                    Long courseId = null;
                    String title;
                    Integer orderNum = null;
                    boolean flag = false;

                    // 1. 강좌 ID 입력 루프
                    while (courseId == null) {
                        try {
                            System.out.print("강좌 ID를 입력하세요: ");
                            String courseIdInput = sc.nextLine();
                            Validator.validateNonBlank(courseIdInput);

                            long id = Long.parseLong(courseIdInput);
                            Validator.validatePositive(id);

                            List<ViewSectionDto> sectionsByCourseId = sectionService.findSectionsByCourseId(id);
                            System.out.println("\n--- [" + id + "]번 강좌의 섹션 목록 ---");

                            if (sectionsByCourseId.isEmpty()) {
                                System.out.println("해당 강좌의 섹션이 존재하지 않습니다.");
                            } else {
                                sectionsByCourseId.forEach(System.out::println);
                            }

                            System.out.println();
                            courseId = id;
                        } catch (NumberFormatException e) {
                            System.out.println("[입력 오류] ID는 숫자로 입력해야 합니다. 다시 입력해주세요.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } catch (RuntimeException e) {
                            System.out.println("생성 중 오류 발생: " + e.getMessage());
                            flag = true;
                            break;
                        }
                    }

                    if (flag) break;

                    while (true) {
                        // 2. 섹션 제목 입력
                        try {
                            System.out.print("섹션 제목을 입력하세요: ");
                            title = sc.nextLine();
                            Validator.validateNonBlank(title);
                        } catch (IllegalArgumentException e) {
                            System.out.println("생성 중 오류 발생: " + e.getMessage() + " 다시 입력해주세요.");
                            continue;
                        }
                        break;
                    }

                    // 3. 섹션 순서 입력 루프
                    while (orderNum == null) {
                        try {
                            System.out.print("섹션 순서를 입력하세요: ");
                            String orderNumInput = sc.nextLine();
                            Validator.validateNonBlank(orderNumInput);

                            int num = Integer.parseInt(orderNumInput);
                            Validator.validatePositive(num);
                            orderNum = num;
                        } catch (NumberFormatException e) {
                            System.out.println("[입력 오류] 순서는 숫자로 입력해야 합니다. 다시 입력해주세요.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    // 4. 모든 입력이 완료된 후 서비스 로직 호출
                    try {
                        sectionService.saveSection(courseId, title, orderNum);
                        System.out.println("섹션이 성공적으로 생성되었습니다.");
                    } catch (RuntimeException e) {
                        System.out.println("[생성 실패] " + e.getMessage());
                    }
                }
                case 2 -> { // 섹션 조회 (ID)
                    while (true) {
                        try {
                            System.out.print("조회할 섹션 ID를 입력하세요: ");
                            String sectionIdInput = sc.nextLine();
                            Validator.validateNonBlank(sectionIdInput);

                            long sectionId = Long.parseLong(sectionIdInput);
                            Validator.validatePositive(sectionId);

                            ViewSectionDto viewSectionDto = sectionService.findSectionById(sectionId);

                            if (viewSectionDto != null) {
                                System.out.println("\n--- 조회 결과 ---");
                                System.out.println(viewSectionDto);
                            } else {
                                System.out.println("해당 ID의 섹션을 찾을 수 없습니다.");
                            }

                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("[입력 오류] 섹션 ID는 숫자로 입력해야 합니다. 다시 입력해주세요.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } catch (RuntimeException e) {
                            System.out.println("조회 중 오류 발생: " + e.getMessage());
                            break;
                        }
                    }
                }
                case 3 -> { // 강좌별 섹션 목록 조회
                    while (true) {
                        try {
                            System.out.print("섹션 목록을 조회할 강좌 ID를 입력하세요: ");
                            String courseIdInput = sc.nextLine();
                            Validator.validateNonBlank(courseIdInput);

                            long courseId = Long.parseLong(courseIdInput);
                            Validator.validatePositive(courseId);

                            List<ViewSectionDto> sections = sectionService.findSectionsByCourseId(courseId);

                            if (sections.isEmpty()) {
                                System.out.println();
                                System.out.println("해당 강좌에 등록된 섹션이 없습니다.");
                            } else {
                                System.out.println("\n--- [" + courseId + "] 강좌의 섹션 목록 ---");
                                sections.forEach(System.out::println);
                            }
                            break;

                        } catch (NumberFormatException e) {
                            System.out.println("[입력 오류] 강좌 ID는 숫자로 입력해야 합니다. 다시 입력해주세요.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } catch (RuntimeException e) {
                            System.out.println("조회 중 오류 발생: " + e.getMessage());
                            break;
                        }
                    }
                }
                case 4 -> { // 섹션 수정
                    Long sectionId = null;
                    String title;
                    Integer orderNum = null;

                    while (sectionId == null) {
                        try {
                            System.out.print("수정할 섹션 ID를 입력하세요: ");
                            String sectionIdInput = sc.nextLine();
                            Validator.validateNonBlank(sectionIdInput);

                            long id = Long.parseLong(sectionIdInput);
                            Validator.validatePositive(id);
                            sectionId = id;
                        } catch (NumberFormatException e) {
                            System.out.println("입력 형식이 올바르지 않습니다. ID와 순서는 숫자로 입력해주세요.");
                            sc.nextLine();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    while (true) {
                        try {
                            System.out.print("수정할 섹션 제목을 입력하세요: ");
                            title = sc.nextLine();
                            Validator.validateNonBlank(title);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        break;
                    }

                    while (orderNum == null) {
                        try {
                            System.out.print("수정할 섹션 번호를 입력하세요: ");
                            String orderNumInput = sc.nextLine();
                            Validator.validateNonBlank(orderNumInput);

                            int num = Integer.parseInt(orderNumInput);
                            Validator.validatePositive(num);

                            orderNum = num;
                            System.out.println();
                        } catch (NumberFormatException e) {
                            System.out.println("입력 형식이 올바르지 않습니다. ID와 순서는 숫자로 입력해주세요.");
                            sc.nextLine();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    try {
                        sectionService.updateSection(sectionId, title, orderNum);
                        System.out.println("섹션이 성공적으로 수정되었습니다.");
                    } catch (RuntimeException e) {
                        System.out.println("[수정 실패] " + e.getMessage());
                    }
                }
                case 5 -> { // 섹션 삭제
                    while (true) {
                        try {
                            System.out.print("삭제할 섹션 번호를 입력하세요: ");
                            String sectionIdInput = sc.nextLine();
                            Validator.validateNonBlank(sectionIdInput);

                            long sectionId = Long.parseLong(sectionIdInput);
                            Validator.validatePositive(sectionId);

                            sectionService.deleteSection(sectionId);
                            System.out.println("섹션이 성공적으로 삭제되었습니다.");
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("[입력 오류] 섹션 ID는 숫자로 입력해야 합니다.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } catch (RuntimeException e) {
                            System.out.println("삭제 실패: " + e.getMessage());
                            break;
                        }
                    }
                }
                case 6 -> { // 이전으로 돌아가기
                    System.out.println("이전 메뉴로 돌아갑니다.");
                    return;
                }
                default -> {
                    System.out.println("1부터 6 사이의 메뉴 번호를 입력해주세요.");
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

