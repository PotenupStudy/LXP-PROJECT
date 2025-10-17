package com.lxp.util;

import com.lxp.model.Role;

import java.util.Objects;

/**
 * 로그인 여부, 유저 상태를 체크하는 클래스
 * - 로그인 여부 -> isSignIn으로 체크
 * - 로그인 한 유저의 ID -> userId로 체크
 * - 로그인 한 유저의 역할 -> role.getValue()로 체크
 */
public class SignInUtil {

    public static boolean isSignIn = false;
    public static long userId;
    public static Role role;

    /**
     * 로그인
     * @param id 로그인 대상 사용자 ID
     */
    public static void signIn(long id, Role r) {
        isSignIn = true;
        userId = id;
        role = r;
    }

    /**
     * 로그아웃
     */
    public static void signOut() {
        isSignIn = false;
        userId = 0;
        role = null;
    }

    /**
     * 이메일 검증
     * @param email 검증할 이메일 문자열
     */
    public static void validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        if(email.isBlank()) {
            throw new IllegalArgumentException("이메일을 입력해 주세요.");
        } else if(email.length() > 255) {
            throw new IllegalArgumentException("이메일은 255자를 넘을 수 없습니다.");
        } else if(!email.matches(emailRegex)) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
    }

    /**
     * 이름 검증
     * @param name 검증할 이름 문자열
     */
    public static void validateName(String name) {
        if(name.isBlank()) {
            throw new IllegalArgumentException("이름을 입력해 주세요.");
        } else if(name.length() > 100) {
            throw new IllegalArgumentException("이름은 100자를 넘을 수 없습니다.");
        }
    }

    /**
     * 현재 로그인한 사용자가 강사(INSTRUCTOR) 권한을 가지고 있는지 확인합니다.
     * 권한이 없으면 RuntimeException을 발생시킵니다.
     */
    public static void validateInstructor() {
        if (role == null || !Objects.equals(role.getValue(), Role.INSTRUCTOR.getValue())) {
            throw new RuntimeException("해당 기능은 강사만 사용할 수 있습니다."); // 메시지를 좀 더 범용적으로 바꿔도 좋습니다.
        }
    }
}
