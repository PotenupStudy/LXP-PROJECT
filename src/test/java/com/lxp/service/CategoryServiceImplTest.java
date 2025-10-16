package com.lxp.service;

import com.lxp.dao.CategoryDAO;
import com.lxp.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryDAO categoryDAO; // 가짜 DAO 객체

    @InjectMocks
    private CategoryServiceImpl categoryService; // 테스트 대상 클래스, 가짜 DAO가 주입됨

    // System.in/out을 백업하고 복원하기 위한 변수
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        // System.out의 출력을 캡처하기 위해 ByteArrayOutputStream으로 리디렉션
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        // 원래의 System.in/out으로 복원
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInput(String data) {
        // 테스트를 위한 사용자 입력 시뮬레이션
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    @DisplayName("모든 카테고리 조회 및 출력 테스트")
    void getAllCategories() throws SQLException {
        // Given
        List<Category> mockCategories = Arrays.asList(
                Category.fromDb(1L, "Java", 1, null, null),
                Category.fromDb(2L, "SQL", 2, null, null)
        );
        when(categoryDAO.getAllCategory()).thenReturn(mockCategories);

        // When
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getCategory_name());

        String expectedOutput = "[1] Java\n[2] SQL\n";
        // assertEquals는 OS별 줄바꿈 문자 차이로 실패할 수 있으므로, contains로 확인
        assertTrue(outContent.toString().replace("\r\n", "\n").contains(expectedOutput));
    }

    @Test
    @DisplayName("카테고리가 없을 때 메시지 출력 테스트")
    void getAllCategories_whenEmpty() throws SQLException {
        // Given
        when(categoryDAO.getAllCategory()).thenReturn(Collections.emptyList());

        // When
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertTrue(result.isEmpty());
        assertTrue(outContent.toString().contains("존재하는 카테고리가 없습니다."));
    }

    @Test
    @DisplayName("특정 카테고리 선택 테스트")
    void selectCategory() throws SQLException {
        // Given
        provideInput("1\n"); // 사용자가 '1'을 입력하는 상황 시뮬레이션
        List<Category> mockCategories = Arrays.asList(
                Category.fromDb(1L, "Java", 1, null, null)
        );
        when(categoryDAO.getAllCategory()).thenReturn(mockCategories);

        // When
        Category selected = categoryService.selectCategory();

        // Then
        assertNotNull(selected);
        assertEquals(1L, selected.getCategory_id());
        assertEquals("Java", selected.getCategory_name());
    }

    @Test
    @DisplayName("새로운 카테고리 저장 테스트")
    void saveCategory() throws SQLException {
        // Given
        String categoryName = "Python";
        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);

        // When
        categoryService.saveCategory();

        // Then
        // categoryDAO의 saveCategory 메소드가 한 번 호출되었는지 검증
        verify(categoryDAO, times(1)).saveCategory(categoryCaptor.capture());

        // DAO에 전달된 Category 객체의 이름이 올바른지 검증
        Category capturedCategory = categoryCaptor.getValue();
        assertEquals(categoryName, capturedCategory.getCategory_name());
        assertNull(capturedCategory.getCategory_id()); // forCreation으로 생성되었으므로 ID는 null이어야 함
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void deleteCategory() throws SQLException {
        // Given
        provideInput("1\n"); // 사용자가 '1'번 카테고리를 선택하는 상황
        Category categoryToDelete = Category.fromDb(1L, "Java", 1, null, null);
        List<Category> mockCategories = Collections.singletonList(categoryToDelete);

        when(categoryDAO.getAllCategory()).thenReturn(mockCategories);

        // When
        categoryService.deleteCategory();

        // Then
        // deleteCategoryById 메소드가 ID가 1L인 Category 객체와 함께 호출되었는지 검증
        verify(categoryDAO, times(1)).deleteCategoryById(categoryToDelete);
    }

    @Test
    @DisplayName("카테고리 이름 수정 테스트")
    void updateCategoryName() throws SQLException {
        // Given
        provideInput("1\nNew Java Name\n"); // '1'번 선택 후, 'New Java Name' 입력
        Category categoryToUpdate = Category.fromDb(1L, "Java", 1, null, null);
        List<Category> mockCategories = Collections.singletonList(categoryToUpdate);

        when(categoryDAO.getAllCategory()).thenReturn(mockCategories);
        // updateCategoryName이 호출되면, 인자로 받은 객체를 그대로 반환하도록 설정
        when(categoryDAO.updateCategoryName(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        categoryService.updateCategoryName();

        // Then
        // updateCategoryName 메소드가 한 번 호출되었는지 검증
        verify(categoryDAO, times(1)).updateCategoryName(any(Category.class));

    }
}