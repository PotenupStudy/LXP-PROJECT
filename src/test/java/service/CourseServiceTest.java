package service;

import com.lxp.config.JDBCConnection;
import com.lxp.model.Course;
import com.lxp.model.CourseLevel;
import com.lxp.service.CourseService;
import com.lxp.service.CourseServiceImpl;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CourseService 기본 CRUD 테스트
 * 각 테스트는 독립적으로 실행 가능
 */
class CourseServiceTest {

    private static Connection connection;
    private CourseService courseService;

    @BeforeAll
    static void init() throws SQLException {
        connection = JDBCConnection.getConnection();
        System.out.println("✅ DB 연결 성공\n");
    }

    @AfterAll
    static void cleanup() throws SQLException {
        // 테스트 데이터 정리
        cleanupTestData();

        if (connection != null) {
            connection.close();
            System.out.println("\n✅ DB 연결 종료");
        }
    }

    @BeforeEach
    void setUp() {
        courseService = new CourseServiceImpl(connection);
    }

    /**
     * 테스트 데이터 정리
     */
    private static void cleanupTestData() {
        try (Statement stmt = connection.createStatement()) {
            // 테스트로 생성된 데이터 삭제
            int deleted = stmt.executeUpdate(
                    "DELETE FROM Course WHERE title LIKE '%테스트%' OR title LIKE '%수정%'"
            );
            if (deleted > 0) {
                System.out.println("🗑️  테스트 데이터 " + deleted + "개 정리 완료");
            }
        } catch (SQLException e) {
            System.err.println("⚠️  테스트 데이터 정리 실패: " + e.getMessage());
        }
    }

    // ========================================
    // CREATE 테스트
    // ========================================

    @Test
    @DisplayName("CREATE - 강좌 생성")
    void createTest() {
        System.out.println("=== CREATE 테스트 시작 ===");

        // Given
        Course newCourse = Course.createCourse(
                1,
                1,
                "테스트 강좌",
                "테스트용 강좌입니다",
                BigDecimal.valueOf(10000),
                CourseLevel.BEGINNER
        );

        // When
        Long savedId = courseService.courseSave(newCourse);

        // Then
        assertNotNull(savedId, "저장된 ID는 null이면 안됨");
        assertTrue(savedId > 0, "저장된 ID는 0보다 커야 함");

        System.out.println("💾 강좌 생성 성공! ID: " + savedId);
        System.out.println("=== CREATE 테스트 완료 ===\n");
    }

    // ========================================
    // READ 테스트
    // ========================================

    @Test
    @DisplayName("READ - 강좌 조회")
    void readTest() {
        System.out.println("=== READ 테스트 시작 ===");

        // When
        List<Course> courses = courseService.courseFindAll();

        // Then
        assertNotNull(courses, "조회 결과는 null이면 안됨");
        assertTrue(courses.size() >= 0, "조회 결과는 0개 이상이어야 함");

        System.out.println("📋 전체 강좌 수: " + courses.size());

        if (!courses.isEmpty()) {
            System.out.println("첫 번째 강좌: " + courses.get(0).getTitle());
        }

        System.out.println("=== READ 테스트 완료 ===\n");
    }

    // ========================================
    // UPDATE 테스트
    // ========================================

    @Test
    @DisplayName("UPDATE - 강좌 수정")
    void updateTest() {
        System.out.println("=== UPDATE 테스트 시작 ===");

        // Given - 수정할 강좌 먼저 생성
        System.out.println("1️⃣  수정할 강좌 생성 중...");
        Course newCourse = Course.createCourse(
                1, 1,
                "테스트 수정용 강좌",
                "수정 테스트용 강좌입니다",
                BigDecimal.valueOf(10000),
                CourseLevel.BEGINNER
        );
        Long savedId = courseService.courseSave(newCourse);
        assertNotNull(savedId, "강좌 생성 실패");
        System.out.println("   ✅ 강좌 생성 완료: ID=" + savedId);

        // 생성한 강좌 조회
        System.out.println("2️⃣  생성한 강좌 조회 중...");
        List<Course> courses = courseService.courseFindAll();
        Course courseToUpdate = courses.stream()
                .filter(c -> c.getCourseId() == savedId.intValue())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("생성한 강좌를 찾을 수 없습니다"));
        System.out.println("   ✅ 강좌 조회 완료: " + courseToUpdate.getTitle());

        String originalTitle = courseToUpdate.getTitle();
        BigDecimal originalPrice = courseToUpdate.getPrice();

        // When - 수정
        System.out.println("3️⃣  강좌 수정 중...");
        courseToUpdate.setTitle("수정된 테스트 강좌");
        courseToUpdate.setPrice(BigDecimal.valueOf(20000));
        courseToUpdate.setCourseLevel(CourseLevel.ADVANCED);

        Long updateResult = courseService.courseUpdateByCourseId(courseToUpdate);

        // Then
        assertEquals(1L, updateResult, "1개 행이 수정되어야 함");

        // 수정 확인
        System.out.println("4️⃣  수정 결과 확인 중...");
        List<Course> updatedCourses = courseService.courseFindAll();
        Course updatedCourse = updatedCourses.stream()
                .filter(c -> c.getCourseId() == savedId.intValue())
                .findFirst()
                .orElseThrow();

        assertEquals("수정된 테스트 강좌", updatedCourse.getTitle());
        assertTrue(BigDecimal.valueOf(20000.00).compareTo(updatedCourse.getPrice()) == 0);
        assertEquals(CourseLevel.ADVANCED, updatedCourse.getCourseLevel());

        System.out.println("✏️  강좌 수정 성공!");
        System.out.println("   - 수정 전: " + originalTitle + ", " + originalPrice + "원, BEGINNER");
        System.out.println("   - 수정 후: " + updatedCourse.getTitle() + ", "
                + updatedCourse.getPrice() + "원, " + updatedCourse.getCourseLevel());
        System.out.println("=== UPDATE 테스트 완료 ===\n");
    }

    // ========================================
    // DELETE 테스트
    // ========================================

    @Test
    @DisplayName("DELETE - 강좌 삭제")
    void deleteTest() {
        System.out.println("=== DELETE 테스트 시작 ===");

        // Given - 삭제할 강좌 먼저 생성
        System.out.println("1️⃣  삭제할 강좌 생성 중...");
        Course newCourse = Course.createCourse(
                1, 1,
                "테스트 삭제용 강좌",
                "삭제 테스트용 강좌입니다",
                BigDecimal.valueOf(5000),
                CourseLevel.BEGINNER
        );
        Long savedId = courseService.courseSave(newCourse);
        assertNotNull(savedId, "강좌 생성 실패");
        System.out.println("   ✅ 강좌 생성 완료: ID=" + savedId);

        // 생성한 강좌 조회
        System.out.println("2️⃣  생성한 강좌 조회 중...");
        List<Course> courses = courseService.courseFindAll();
        Course courseToDelete = courses.stream()
                .filter(c -> c.getCourseId() == savedId.intValue())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("생성한 강좌를 찾을 수 없습니다"));
        System.out.println("   ✅ 강좌 조회 완료: " + courseToDelete.getTitle());

        // When - 삭제
        System.out.println("3️⃣  강좌 삭제 중...");
        Long deleteResult = courseService.courseDeleteByCourseId(savedId.intValue());

        // Then
        assertEquals(1L, deleteResult, "1개 행이 삭제되어야 함");

        // 삭제 확인
        System.out.println("4️⃣  삭제 결과 확인 중...");
        List<Course> afterDelete = courseService.courseFindAll();
        boolean exists = afterDelete.stream()
                .anyMatch(c -> c.getCourseId() == savedId.intValue());

        assertFalse(exists, "삭제된 강좌는 조회되면 안됨");

        System.out.println("🗑️  강좌 삭제 성공! ID: " + savedId);
        System.out.println("=== DELETE 테스트 완료 ===\n");
    }

    @Test
    @DisplayName("EXISTS CHECK - 강좌 존재여부")
    void courseExistsTest() {
        System.out.println("=== EXISTS CHECK 테스트 시작 ===");

        // When
        Boolean result = courseService.courseExists(4);

        // Then
        assertTrue(result, "course_id가 " + 4 + "인 로우 존재");

        System.out.println("💾 강좌 존재여부 테스트 성공! ID: " + 4);
        System.out.println("=== EXISTS CHECK 테스트 완료 ===\n");
    }

    // ========================================
    // 통합 CRUD 플로우 테스트 (보너스)
    // ========================================

    @Test
    @DisplayName("전체 CRUD 플로우 테스트")
    void fullCrudFlowTest() {
        System.out.println("=== 전체 CRUD 플로우 테스트 시작 ===\n");

        // 1. CREATE
        System.out.println("1️⃣  CREATE - 강좌 생성");
        Course newCourse = Course.createCourse(
                1, 1, "플로우 테스트 강좌", "CRUD 플로우 테스트용",
                BigDecimal.valueOf(30000), CourseLevel.INTERMEDIATE
        );
        Long savedId = courseService.courseSave(newCourse);
        assertNotNull(savedId);
        System.out.println("   ✅ 생성 완료: ID=" + savedId + "\n");

        // 2. READ
        System.out.println("2️⃣  READ - 강좌 조회");
        List<Course> courses = courseService.courseFindAll();
        Course foundCourse = courses.stream()
                .filter(c -> c.getCourseId() == savedId.intValue())
                .findFirst()
                .orElseThrow();
        assertEquals("플로우 테스트 강좌", foundCourse.getTitle());
        System.out.println("   ✅ 조회 완료: " + foundCourse.getTitle() + "\n");

        // 3. UPDATE
        System.out.println("3️⃣  UPDATE - 강좌 수정");
        foundCourse.setTitle("수정된 플로우 테스트");
        foundCourse.setPrice(BigDecimal.valueOf(40000));
        Long updateResult = courseService.courseUpdateByCourseId(foundCourse);
        assertEquals(1L, updateResult);

        // 재조회
        List<Course> updatedCourses = courseService.courseFindAll();
        Course updatedCourse = updatedCourses.stream()
                .filter(c -> c.getCourseId() == savedId.intValue())
                .findFirst()
                .orElseThrow();
        assertEquals("수정된 플로우 테스트", updatedCourse.getTitle());
        System.out.println("   ✅ 수정 완료: " + updatedCourse.getTitle() + "\n");

        // 4. DELETE
        System.out.println("4️⃣  DELETE - 강좌 삭제");
        Long deleteResult = courseService.courseDeleteByCourseId(savedId.intValue());
        assertEquals(1L, deleteResult);

        // 삭제 확인
        List<Course> finalCourses = courseService.courseFindAll();
        boolean exists = finalCourses.stream()
                .anyMatch(c -> c.getCourseId() == savedId.intValue());
        assertFalse(exists);
        System.out.println("   ✅ 삭제 완료\n");

        System.out.println("🎉 전체 CRUD 플로우 테스트 성공!\n");
        System.out.println("=== 전체 CRUD 플로우 테스트 완료 ===\n");
    }
}