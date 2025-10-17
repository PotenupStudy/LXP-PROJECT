package com.lxp.util;

public class Validator {

    public static int selectValidator(int listSize) {
        while (true) {
            int choice = InputUtil.readValidInt("번호를 선택해 주세요.");

            if (choice >= 1 && choice <= listSize) {
                return choice;
            } else {
                System.err.printf("⚠️ 오류: 1부터 %d 사이의 번호를 입력해주세요.\n", listSize);
                System.err.println("==================================================================");
            }
        }
    }

    public static boolean selectValidator(int choice, int listSize) {
        if (choice >= 1 && choice <= listSize) {
            return false;
        } else {
            System.err.printf("⚠️ 오류: 1부터 %d 사이의 번호를 입력해주세요.\n", listSize);
            System.err.println("==================================================================");
            return true;
        }

    }
    public static boolean stringValidator(String str) {
        if(str.trim().isEmpty()){
            return true;
        }
        return false;
    }
}
