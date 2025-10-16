import config.JDBCConnection;
import controller.UserController;
import model.dto.RegisterUserDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = JDBCConnection.getConnection();

            Scanner sc = new Scanner(System.in);
            while(true) {
                System.out.println("===================================");
                System.out.println("==      Outflearn 강의 시스템      ==");
                System.out.println("===================================");
                System.out.println("= 1. 회원 관련 업무                 =");
                System.out.println("===================================");

                int num = sc.nextInt();
                if(num == 1) {
                    runUserFeature(conn);
                } else {
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void runUserFeature(Connection conn) {
        UserController userController = new UserController(conn);
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println();
            System.out.println("===================================");
            System.out.println("==   [Outflearn] - 회원 관련 업무  ==");
            System.out.println("===================================");
            System.out.println("== 1. 회원 가입                   ==");
            System.out.println("== 2. 로그인                      ==");
            System.out.println("== 3. 회원 정보 조회               ==");
            System.out.println("== 4. 회원 정보 수정               ==");
            System.out.println("== 5. 회원 탈퇴                   ==");
            System.out.println("== 6. 이전으로 돌아가기             ==");
            System.out.println("===================================");
            int num = sc.nextInt();
            sc.nextLine();

            switch(num) {
                case 1 -> {     // 회원 가입
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
                case 2 -> System.out.println("로그인");
                case 3 -> System.out.println("회원 정보 조회");
                case 4 -> System.out.println("회원 정보 수정");
                case 5 -> System.out.println("회원 탈퇴");
                case 6 -> {
                    return;
                }
                default -> System.out.println("잘못 된 입력입니다.");
            }
        }
    }
}
