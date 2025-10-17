package com.lxp.utill;

import java.util.Scanner;

public final class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    private InputUtil(){
    }

    public static String readString(String prompt) {
        System.out.printf("▶ %s\n",prompt);
        return scanner.nextLine();
    }

    public static int readValidInt(String prompt) {
        String input = readString(prompt);
        while(true){
            try{
                return Integer.parseInt(input);
            }catch(NumberFormatException e){
                System.err.println("⚠️ 오류: 숫자로만 입력해야 합니다.\n");
            }
        }
    }
}
