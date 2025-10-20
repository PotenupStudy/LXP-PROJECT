package com.lxp.util;

import java.util.Scanner;

public final class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    private InputUtil(){
    }

    public static String readString(String prompt) {
        System.out.printf("▶ %s\n",prompt);
        return readString();
    }
    public static String readString() {
        while (true) {
            try {
                String str = scanner.nextLine();
                Validator.validateNonBlank(str);
                return str;
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static int readValidInt(String prompt) {
        while(true){
            try{
                String input = readString(prompt);
                return Integer.parseInt(input);
            }catch(NumberFormatException e){
                System.err.println("⚠️ 오류: 숫자로만 입력해야 합니다.\n");
            }
        }
    }
    public static int readValidInt() {
        while(true){
            try{
                String input = readString();
                return Integer.parseInt(input);
            }catch(NumberFormatException e){
                System.err.println("⚠️ 오류: 숫자로만 입력해야 합니다.\n");
            }
        }
    }
}
