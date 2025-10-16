package com.lxp.utill;

import java.util.Scanner;

public class Validator {

    public static int selectValidator(int listSize) {
        while(true){
            int choice = InputUtil.readValidInt("번호를 입력해 주세요.");

            if (choice >= 1 && choice <= listSize) {
                return choice;
            } else {
                System.err.printf("⚠️ 오류: 1부터 %d 사이의 번호를 입력해주세요.\n", listSize);
                System.err.println("==================================================================");
            }
        }
    }
}
