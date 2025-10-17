package com.lxp.input;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// TODO:입력/검증 분리 필요
public class StringInputReader implements InputReader<String>{
    private final Scanner scanner;
    private final List<String> allowedValues;

    StringInputReader(Scanner scanner, List<String> allowedValues) {
        if (allowedValues == null || allowedValues.isEmpty())
            throw new IllegalArgumentException("허용된 값을 최소 하나 이상 지정해야 합니다.");

        this.scanner = scanner;
        this.allowedValues = allowedValues.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    @Override
    public String read() {
        while (true){
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.isEmpty()) {
                System.out.println("❌ 입력이 비어있습니다.");
                continue;
            }

            if(!allowedValues.contains(input)){
                System.out.println("유효 범위를 벗어났습니다. " + allowedValues.toString() + "중 하나를 입력해주세요." );
                continue;
            }

            return input;

        }
    }
}
