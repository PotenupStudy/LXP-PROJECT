package com.lxp;

import com.lxp.config.JDBCConnection;
import com.lxp.model.Section;
import com.lxp.service.CourseService;
import com.lxp.service.SectionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Application {
    public static void main(String[] args) {
        try (Connection connection = JDBCConnection.getConnection()) {
            System.out.println("DB 연결 성공");

            SectionService sectionService = new SectionService(connection);
            CourseService courseService = new CourseService(connection);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n==================================");
                System.out.println("원하는 작업의 번호를 입력해주세요.");
                System.out.println("0. 프로그램 종료");
                System.out.println("1. 섹션 등록");
                System.out.println("2. 섹션 조회");
                System.out.println("3. 섹션 수정");
                System.out.println("4. 섹션 삭제");
                System.out.println("==================================");
                System.out.print("입력 > ");

                int userInput;
                try {
                    userInput = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("숫자를 입력해주세요.");
                    scanner.nextLine(); // 잘못된 입력을 비워줍니다.
                    continue; // 루프의 처음으로 돌아갑니다.
                }
                scanner.nextLine(); // 숫자 입력 후 남은 줄바꿈 문자를 처리합니다.

                // 2. 사용자가 0을 입력하면 break를 통해 while 루프를 종료합니다.
                if (userInput == 0) {
                    System.out.println("프로그램을 종료합니다.");
                    break;
                }

                // 3. 사용자가 1을 입력하면 기존 섹션 등록 로직을 실행합니다.
                if (userInput == 1) {
                    System.out.print("강좌 ID를 입력해주세요: ");
                    long courseId = scanner.nextLong();
                    scanner.nextLine();

                    if (!courseService.courseExists(courseId)) {
                        System.out.println("존재하지 않는 강좌 ID입니다. 메뉴로 돌아갑니다.");
                        continue; // ID가 없으면 루프의 처음으로 돌아갑니다.
                    }

                    System.out.print("섹션 명을 입력해주세요: ");
                    String title = scanner.nextLine();

                    System.out.print("섹션 번호를 입력해주세요: ");
                    int orderNum = scanner.nextInt();
                    scanner.nextLine();

                    if (sectionService.sectionOrderExists(courseId, orderNum)) {
                        System.out.println("해당 강좌에 이미 존재하는 섹션 번호입니다. 메뉴로 돌아갑니다.");
                        continue; // 중복되면 처음 메뉴로 돌아갑니다.
                    }

                    Section section = new Section(courseId, title, orderNum);
                    long result = sectionService.saveSection(section);

                    if (result > 0) {
                        System.out.println("\n[등록 완료]");
                        System.out.println("Section Info: " + section);
                    } else {
                        System.out.println("모듈 등록에 실패했습니다.");
                    }
                } else if (userInput == 2) {
                    System.out.print("조회할 강좌 ID를 입력해주세요: ");
                    long courseIdToFind = scanner.nextLong();
                    scanner.nextLine();

                    if (!courseService.courseExists(courseIdToFind)) {
                        System.out.println("존재하지 않는 강좌 ID입니다. 메뉴로 돌아갑니다.");
                        continue;
                    }

                    // Service를 통해 섹션 목록을 가져옵니다.
                    List<Section> sections = sectionService.findSectionsByCourseId(courseIdToFind);

                    // 조회 결과에 따라 다른 메시지를 출력합니다.
                    if (sections.isEmpty()) {
                        System.out.println("해당 강좌에는 등록된 섹션이 없습니다.");
                    } else {
                        System.out.println("\n[" + courseIdToFind + "번 강좌의 섹션 목록]");
                        for (Section s : sections) {
                            System.out.println("  - [" + s.getOrderNum() + "]: " + s.getTitle());
                        }
                    }
                } else if (userInput == 3) {
                    System.out.print("수정할 섹션의 강좌 ID를 입력해주세요: ");
                    long courseIdToUpdate = scanner.nextLong();
                    scanner.nextLine();

                    if (!courseService.courseExists(courseIdToUpdate)) {
                        System.out.println("존재하지 않는 강좌 ID입니다.");
                        continue;
                    }

                    System.out.print("수정할 섹션의 순서 번호를 입력해주세요: ");
                    int orderNumToUpdate = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("새로운 섹션 제목을 입력해주세요: ");
                    String newTitle = scanner.nextLine();

                    Section sectionToUpdate = new Section(courseIdToUpdate, newTitle, orderNumToUpdate);
                    if (sectionService.updateSection(sectionToUpdate)) {
                        System.out.println("\n[수정 완료]");
                    } else {
                        System.out.println("섹션 수정에 실패했습니다 (존재하지 않는 섹션 번호일 수 있습니다).");
                    }

                } else if (userInput == 4) {
                    // --- 4. 섹션 삭제 로직 ---
                    System.out.print("삭제할 섹션의 강좌 ID를 입력해주세요: ");
                    long courseIdToDelete = scanner.nextLong();
                    scanner.nextLine();

                    if (!courseService.courseExists(courseIdToDelete)) {
                        System.out.println("존재하지 않는 강좌 ID입니다.");
                        continue;
                    }

                    System.out.print("삭제할 섹션의 번호를 입력해주세요: ");
                    int orderNumToDelete = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("정말로 삭제하시겠습니까? (Y/N): ");
                    String confirm = scanner.nextLine();

                    if ("Y".equalsIgnoreCase(confirm)) {
                        if (sectionService.deleteSection(courseIdToDelete, orderNumToDelete)) {
                            System.out.println("\n[삭제 완료]");
                        } else {
                            System.out.println("섹션 삭제에 실패했습니다 (존재하지 않는 섹션 번호일 수 있습니다).");
                        }
                    } else {
                        System.out.println("삭제를 취소했습니다.");
                    }
                } else {
                    // 0이나 1이 아닌 다른 번호를 입력했을 경우
                    System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
                }
            }
        } catch (SQLException e) {
            System.err.println("데이터베이스 처리 중 오류가 발생했습니다.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("알 수 없는 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
}
