package service;

import dao.UserDao;
import model.Role;
import model.dto.RegisterUserDto;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * User Service 레이어
 *
 * User 요청에 관련된 로직들 수행
 */
public class UserService {

    private UserDao userDao;

    public UserService(Connection conn) {
        userDao = new UserDao(conn);
    }

    /**
     * 회원 등록
     * @param registerUserDto 회원 등록에 관한 정보 DTO
     * @return 가입 된 유저 ID
     */
    public long registerUser(RegisterUserDto registerUserDto) {
        try {
            return userDao.addUser(registerUserDto);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
