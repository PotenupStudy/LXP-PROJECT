package service;

import dao.UserDao;
import model.User;
import model.dto.RegisterUserDto;
import model.dto.ViewUserDto;
import util.SignInUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * User Service 레이어
 * - User 요청에 관련된 로직들 수행
 */
public class UserService {

    private final UserDao userDao;

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

    public boolean signInUser(String email) {
        if(SignInUtil.isSignIn) {
            return false;
        }

        try {
            User foundUser = userDao.findUserByEmail(email);
            if(foundUser == null) {
                throw new RuntimeException("사용자를 찾을 수 없습니다.");
            }

            SignInUtil.isSignIn = true;
            SignInUtil.userId = foundUser.getUserId();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
