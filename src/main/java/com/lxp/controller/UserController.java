package com.lxp.controller;

import com.lxp.model.dto.RegisterUserDto;
import com.lxp.model.dto.ViewUserDto;
import com.lxp.service.UserService;
import com.lxp.util.SignInUtil;

import java.sql.Connection;

/**
 * UserController
 * - User 관련 요청을 받아서 Service에게 전달
 * - 만들어진 User 관련 응답을 반환
 */
public class UserController {

    private final UserService userService;

    public UserController(Connection conn) {
        userService = new UserService(conn);
    }

    /**
     * 회원 등록 요청
     * @param registerUserDto 회원 등록에 관한 정보 DTO
     */
    public long registerUser(RegisterUserDto registerUserDto) {
        // 입력 값 검증
        registerUserDto.validate();

        return userService.registerUser(registerUserDto);
    }

    /**
     * 회원 로그인 요청
     * @param email 사용자 이메일
     * @return 로그인 성공 여부
     */
    public boolean signInUser(String email) {
        // 입력 값 검증(이메일)
        SignInUtil.validateEmail(email);
        return userService.signInUser(email);
    }

    /**
     * 회원 정보 조회 요청
     * @return 요청 대상 회원의 정보
     */
    public ViewUserDto viewUser(long userId) {
        return userService.viewUser(userId);
    }

    /**
     * 회원 정보 수정 요청
     * @param name   수정 할 이름
     * @param userId 사용자 ID
     * @return 수정 되었는지 여부
     */
    public int editUserInfo(String name, long userId) {
        // 입력 값 검증(이름)
        SignInUtil.validateName(name);

        return userService.editUserInfo(name, userId);
    }

    /**
     * 회원 탈퇴 요청
     * @param userId 탈퇴 할 사용자 ID
     * @return 탈퇴 성공 여부
     */
    public int withdrawalUser(long userId) {
        return userService.withdrawalUser(userId);
    }
}
