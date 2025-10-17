package com.lxp.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IntegerInputReader implements InputReader<Integer>{
    private final Scanner scanner;
    private final int minValue;
    private final int maxValue;

    IntegerInputReader(Scanner scanner, int minValue, int maxValue) {
        if (minValue > maxValue)
            throw new IllegalArgumentException("최소값은 최대값보다 클 수 없습니다.");

        this.scanner = scanner;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public Integer read() {
        int input;
        while (true){
            try {
                input = scanner.nextInt();

                if(minValue > input || input > maxValue){
                    System.out.println("유효 범위를 벗어났습니다. " + minValue + " ~ " + maxValue + "사이의 정수만 입력할 수 있습니다." );
                    continue;
                }

                return input;
            }  catch (InputMismatchException e) {
                System.out.println(e.getMessage() + "❌ 올바른 숫자를 입력하세요.");
                scanner.next();
            }
        }


    }
}
