package com.lxp.util;

public class Validator {

    public static boolean selectValidator(int choice, int listSize) {
        if (choice >= 1 && choice <= listSize) {
            return false;
        } else {
            System.err.printf("⚠️ 오류: 1부터 %d 사이의 번호를 입력해주세요.\n", listSize);
            System.err.println("==================================================================");
            return true;
        }
    }

    /**
     * 문자열이 비어있는지 검증합니다.
     *
     * @param input 검증할 문자열
     * @throws IllegalArgumentException 문자열이 비어있을 경우
     */
    public static void validateNonBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("⚠️ 오류: 빈 값은 입력될수 없습니다.");
        }
    }

    /**
     * 숫자가 양수인지 검증합니다.
     *
     * @param number 검증할 long 타입 숫자
     * @throws IllegalArgumentException 숫자가 0 이하일 경우
     */
    public static void validatePositive(long number) {
        if (number <= 0) {
            throw new IllegalArgumentException("⚠️ 오류: ID는 0보다 커야 합니다.");
        }
    }

    /**
     * 숫자가 양수인지 검증합니다.
     *
     * @param number 검증할 int 타입 숫자
     * @throws IllegalArgumentException 숫자가 0 이하일 경우
     */
    public static void validatePositive(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("⚠️ 오류: 숫자는 0보다 커야 합니다.");
        }
    }
    public static boolean compareStringToIgnoreCase(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return str1 != str2;
        }
        return !str1.trim().equalsIgnoreCase(str2.trim());
    }
}
