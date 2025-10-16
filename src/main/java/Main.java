import config.JDBCConnection;
import controller.UserController;
import model.dto.RegisterUserDto;
import util.SignInUtil;

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
            System.out.println(e.getMessage() + "\n" + e.getStackTrace()[0]);
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
            System.out.println("== 3. 로그아웃                    ==");
            System.out.println("== 4. 회원 정보 조회               ==");
            System.out.println("== 5. 회원 정보 수정               ==");
            System.out.println("== 6. 회원 탈퇴                   ==");
            System.out.println("== 7. 이전으로 돌아가기             ==");
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
                case 2 -> {     // 로그인
                    if(SignInUtil.isSignIn) {
                        System.out.println("이미 로그인 되어 있습니다.");
                        continue;
                    }
                    System.out.print("이메일을 입력하세요 : ");
                    String email = sc.nextLine();

                    if(userController.signInUser(email)) {
                        System.out.println("로그인 성공");
                    }
                }
                case 3 -> {     // 로그아웃
                    if(!SignInUtil.isSignIn) {
                        System.out.println("로그인 되지 않았습니다.");
                        continue;
                    }

                    SignInUtil.isSignIn = false;
                    SignInUtil.userId = 0;
                }
                case 4 -> {     // 회원 정보 조회
                    System.out.println(userController.viewUser());
                }
                case 5 -> {
                    System.out.print("변경 할 이름을 입력하세요 : ");
                    String name = sc.nextLine();
                    if(userController.editUserInfo(name, SignInUtil.userId) > 0) {
                        System.out.println("변경 완료");
                    }
                }
                case 6 -> System.out.println("회원 탈퇴");
                case 7 -> {
                    return;
                }
                default -> System.out.println("잘못 된 입력입니다.");
            }
        }
    }
}
