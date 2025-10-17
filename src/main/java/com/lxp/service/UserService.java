package com.lxp.service;

import com.lxp.dao.UserDao;
import com.lxp.model.User;
import com.lxp.model.dto.RegisterUserDto;
import com.lxp.model.dto.ViewUserDto;
import com.lxp.util.SignInUtil;

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
            // 탈퇴 된 회원 이메일 여부 체크
            if(userDao.isDeletedUser(registerUserDto.getEmail())) {
                throw new IllegalArgumentException("탈퇴 된 회원입니다.");
            }

            // 이메일 중복 체크
            if(userDao.findUserByEmail(registerUserDto.getEmail()) != null) {
                throw new IllegalArgumentException("중복 된 이메일 입니다");
            }

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
        if(SignInUtil.isSignIn) {      // 이미 로그인 상태
            return false;
        }

        try {
            User foundUser = userDao.findUserByEmail(email);
            if(foundUser == null) {
                throw new RuntimeException("사용자를 찾을 수 없습니다.");
            }

            SignInUtil.signIn(foundUser.getUserId() ,foundUser.getRole());

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 회원 정보 조회
     * @param userId 유저 ID
     * @return 회원 정보를 담은 DTO
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
