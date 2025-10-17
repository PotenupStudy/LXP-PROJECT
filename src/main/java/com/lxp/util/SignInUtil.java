package com.lxp.util;

public class SignInUtil {

    public static boolean isSignIn = false;
    public static long userId;

    /**
     * 로그인
     * @param id 로그인 대상 사용자 ID
     */
    public static void signIn(long id) {
        isSignIn = true;
        userId = id;
    }

    /**
     * 로그아웃
     */
    public static void signOut() {
        isSignIn = false;
        userId = 0;
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
}
