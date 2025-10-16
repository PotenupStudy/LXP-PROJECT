package controller;

import model.Role;
import model.dto.RegisterUserDto;
import service.UserService;

import java.sql.Connection;

/**
 * UserController
 *
 * - User 관련 요청을 받아서 Service에게 전달
 * - 만들어진 User 관련 응답을 반환
 */
public class UserController {

    private final Connection conn;
    private UserService userService;

    private static String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public UserController(Connection conn) {
        this.conn = conn;
        userService = new UserService(conn);
    }

    /**
     * 회원 등록
     * @param registerUserDto 회원 등록에 관한 정보 DTO
     */
    public long registerUser(RegisterUserDto registerUserDto) {
        // 입력 값 예외 처리
        if(registerUserDto.getName().isBlank()) {
            throw new IllegalArgumentException("이름을 입력해 주세요.");
        } else if(registerUserDto.getName().length() > 100) {
            throw new IllegalArgumentException("이름은 100자를 넘을 수 없습니다.");
        } else if(registerUserDto.getEmail().isBlank()) {
            throw new IllegalArgumentException("이메일을 입력해 주세요.");
        } else if(registerUserDto.getEmail().length() > 255) {
            throw new IllegalArgumentException("이메일은 255자를 넘을 수 없습니다.");
        } else if(!registerUserDto.getEmail().matches(emailRegex)) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        } else if(registerUserDto.getRole().equals(Role.ANONYMOUS)) {
            throw new IllegalArgumentException("올바른 역할이 아닙니다.");
        }

        return userService.registerUser(registerUserDto);
    }
}
