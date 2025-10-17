package input.service;

import com.lxp.input.InputReaderFactory;
import com.lxp.model.CourseLevel;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class InputReaderFactoryTest {

    private static InputReaderFactory inputReaderFactory;

    private static InputReaderFactory factoryWithInputs(String... lines) {
        String joined = String.join("\n", lines) + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(joined.getBytes(StandardCharsets.UTF_8)));
        return new InputReaderFactory(scanner);
    }

    // ========================================
    // CREATE 테스트
    // ========================================

    @Test
    @DisplayName("정수 입력")
    void InteagerInputReaderTest() {
        System.out.println("=== 정수 입력 테스트 시작 ===");

        // Given
        final int MIN = 1;
        final int MAX = 10;

        inputReaderFactory = factoryWithInputs("-1");

        // When
        int result = inputReaderFactory.integerInRange(MIN, MAX).read();

        // Then
        assertTrue(result >= MIN && result <= MAX, "저장된 정수[" + result + "]는 " + MIN + "보다 크고 " + MAX + "보다 작아야한다.");
        System.out.println("💾 정수 입력 성공! result : " + result);
        System.out.println("=== 정수 입력 테스트 완료 ===\n");
    }

    @Test
    @DisplayName("문자열 입력")
    void StringInputReaderTest() {
        System.out.println("=== 문자열 입력 테스트 시작 ===");

        // Given
        inputReaderFactory = factoryWithInputs("BEGINNER");
        List<String> allowedValues = Arrays.stream(CourseLevel.values())
                .map(courseLevel -> courseLevel.getValue())
                .toList();

        // When
        String result = inputReaderFactory.stringIn(allowedValues).read();

        // Then
        assertTrue(allowedValues.contains(result.toLowerCase()), allowedValues + " 내의 값을 선택해야합니다.\n[" + result + "]를 입력했습니다.");
        System.out.println("💾 정수 입력 성공! result : " + result);
        System.out.println("=== 정수 입력 테스트 완료 ===\n");
    }

}