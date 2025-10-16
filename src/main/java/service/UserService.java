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

    /**
     * 로그인
     * @param email 사용자 이메일
     * @return 성공 여부
     */
    public boolean signInUser(String email) {
        if(SignInUtil.isSignIn) {
            return false;
        }

        try {
            User foundUser = userDao.findUserByEmail(email);
            if(foundUser == null) {
                throw new RuntimeException("사용자를 찾을 수 없습니다.");
            }

            SignInUtil.signIn(foundUser.getUserId());

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 회원 정보 조회
     * @param userId 유저 ID
     * @return
     */
    public ViewUserDto viewUser(long userId) {
        try {
            User foundUser = userDao.findUser(userId);
            return ViewUserDto.from(foundUser);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 사용자 정보 수정
     * @param name      변경할 사용자 이름
     * @param userId    사용자 ID
     * @return 수정 성공 여부
     */
    public int editUserInfo(String name, long userId) {
        try {
            return userDao.updateUser(name, userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 사용자 탈퇴
     * @param userId 탈퇴할 사용자 ID
     * @return 탈퇴 성공 여부
     */
    public int withdrawalUser(long userId) {
        try {
            return userDao.deleteUser(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
