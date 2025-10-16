package dao;

import model.dto.RegisterUserDto;
import util.QueryUtil;

import java.sql.*;

/**
 * User와 관련된 데이터베이스 작업
 */
public class UserDao {

    private final Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * 사용자 추가
     * @param registerUserDto 회원 등록에 관한 정보 DTO
     * @return 추가된 사용자 ID
     * @throws SQLException SQL 관련 예외
     */
    public long addUser(RegisterUserDto registerUserDto) throws SQLException, IllegalArgumentException {
        String sql = QueryUtil.getQuery("user.save");
        String findSql = QueryUtil.getQuery("user.findByEmail");

        // 이메일 중복 체크
        try(PreparedStatement pstmt = conn.prepareStatement(findSql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, registerUserDto.getEmail());

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                throw new IllegalArgumentException("중복 된 이메일 입니다");
            }
        }

        // 회원가입 데이터베이스 처리
        try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, registerUserDto.getName());
            pstmt.setString(2, registerUserDto.getEmail());
            pstmt.setString(3, registerUserDto.getRole().getValue());

            int result = pstmt.executeUpdate();

            if(result > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if(rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        }

        throw new SQLException("강좌 생성에 실패했습니다.");
    }
}
