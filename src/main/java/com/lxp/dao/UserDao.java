package com.lxp.dao;

import com.lxp.model.Role;
import com.lxp.model.User;
import com.lxp.model.dto.RegisterUserDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        if(findUserByEmail(registerUserDto.getEmail()) != null) {
            throw new IllegalArgumentException("중복 된 이메일 입니다");
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

    /**
     * 이메일로 사용자 찾기
     * @param email 사용자 이메일
     * @return 해당하는 User
     * @throws SQLException
     */
    public User findUserByEmail(String email) throws SQLException {
        String sql = QueryUtil.getQuery("user.findByEmail");

        // 이메일 중복 체크
        try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                User foundUser = new User(
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        Role.findByString(rs.getString("role")),
                        LocalDateTime.parse(rs.getString("insert_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        LocalDateTime.parse(rs.getString("modify_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        rs.getString("delete_date") != null?
                                LocalDateTime.parse(rs.getString("delete_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                : null
                );

                return foundUser;
            }

            return null;
        }
    }

    /**
     * user id에 해당하는 사용자 찾기
     * @param userId 사용자 ID
     * @return 해당하는 User
     * @throws SQLException
     */
    public User findUser(long userId) throws SQLException {
        String sql = QueryUtil.getQuery("user.find");

        // 회원 정보 찾기 데이터베이스 처리
        try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return new User(
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        Role.findByString(rs.getString("role")),
                        LocalDateTime.parse(rs.getString("insert_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        LocalDateTime.parse(rs.getString("modify_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        rs.getString("delete_date") != null?
                                LocalDateTime.parse(rs.getString("delete_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                : null
                );
            }

            return null;
        }
    }

    /**
     * user id에 해당하는 유저 정보 업데이트
     * @param name   사용자 이름
     * @param userId 사용자 ID
     * @return 변경 되었는지의 여부
     * @throws SQLException
     */
    public int updateUser(String name, long userId) throws SQLException {
        String query = QueryUtil.getQuery("user.update");

        int rowsAffected = 0;

        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setLong(2, userId);

            rowsAffected = pstmt.executeUpdate();
        }

        return rowsAffected;
    }

    /**
     * 사용자 삭제(Soft delete)
     * @param userId 삭제할 사용자 ID
     * @return 삭제 성공 여부
     * @throws SQLException
     */
    public int deleteUser(long userId) throws SQLException {
        String query = QueryUtil.getQuery("user.softDelete");

        int rowsAffected = 0;

        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pstmt.setLong(2, userId);

            rowsAffected = pstmt.executeUpdate();
        }

        return rowsAffected;
    }
}
