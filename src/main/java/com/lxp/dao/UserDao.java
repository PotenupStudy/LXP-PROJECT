package com.lxp.dao;

import com.lxp.model.Role;
import com.lxp.model.User;
import com.lxp.model.dto.RegisterUserDto;
import com.lxp.util.QueryUtil;

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

        throw new SQLException("회원 생성에 실패했습니다.");
    }

    /**
     * 이메일로 사용자 찾기
     * @param email 사용자 이메일
     * @return 해당하는 User
     * @throws SQLException
     */
    public User findUserByEmail(String email) throws SQLException {
        String sql = QueryUtil.getQuery("user.findByEmail");

        // 이메일 기반 회원 검색 데이터베이스 처리
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
            } else {
                throw new RuntimeException("일치하는 회원이 존재하지 않습니다.");
            }
        } catch(SQLException ex) {
            throw new SQLException("회원 검색에 실패했습니다.");
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

        // ID 기반 회원 정보 찾기 데이터베이스 처리
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
        }

        throw new SQLException("회원 검색에 실패했습니다.");
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

            return rowsAffected;
        } catch(SQLException ex) {
            throw new SQLException("회원 정보 업데이트에 실패했습니다.");
        }
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

            return rowsAffected;
        } catch(SQLException ex) {
            throw new SQLException("회원 탈퇴에 실패했습니다.");
        }
    }

    /**
     * 삭제 된 유저인지 체크
     * @param email 검색 대상 이메일
     * @return 삭제 된 유저 유무
     * @throws SQLException
     */
    public boolean isDeletedUser(String email) throws SQLException {
        String sql = QueryUtil.getQuery("user.findByEmail");

        try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            // delete_date가 존재하면 삭제 된 유저
            if(rs.next() && rs.getString("delete_date") != null) {
                return true;
            }

            return false;
        } catch(SQLException ex) {
            throw new SQLException("유저 삭제 유무 체크에 실패했습니다.");
        }
    }
}
