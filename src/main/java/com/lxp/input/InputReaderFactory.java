package com.lxp.input;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

// TODO:입력/검증 분리 필요
public final class InputReaderFactory {

    private final Scanner scanner;

    public InputReaderFactory(Scanner scanner) {
        this.scanner = Objects.requireNonNull(scanner, "scanner must be required");
    }

    /* ---------- Integer ---------- */

    /** [정수] min ~ max 사이의 값을 읽음(포함 범위) */
    public InputReader<Integer> integerInRange(int min, int max) {
        return new IntegerInputReader(scanner, min, max);
    }

    /* ---------- String ---------- */

    /** [문자열] 허용 값 집합(대소문자 구분하지 않음) 중 하나를 읽음 */
    public InputReader<String> stringIn(List<String> allowedValues) {
        return new StringInputReader(scanner, allowedValues);
    }
}
