package com.lxp.model.dto;

import com.lxp.model.Role;

public class RegisterUserDto {

    private final String name;
    private final String email;
    private final Role role;

    public RegisterUserDto(String name, String email, String roleStr) {
        this.name = name;
        this.email = email;
        this.role = roleStr.equals("1")? Role.STUDENT :
                roleStr.equals("2")? Role.INSTRUCTOR : Role.ANONYMOUS;
    }

    // Getter
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role;
    }

    /**
     * 입력 값 검증
     * - name : 빈칸x, 100자 이하
     * - email: 빈칸x, 255자 이하, 이메일 형식 준수
     * - role : 1(student), 2(instructor) 중 하나 선택
     */
    public void validate() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if(name.isBlank()) {
            throw new IllegalArgumentException("이름을 입력해 주세요.");
        } else if(name.length() > 100) {
            throw new IllegalArgumentException("이름은 100자를 넘을 수 없습니다.");
        } else if(email.isBlank()) {
            throw new IllegalArgumentException("이메일을 입력해 주세요.");
        } else if(email.length() > 255) {
            throw new IllegalArgumentException("이메일은 255자를 넘을 수 없습니다.");
        } else if(!email.matches(emailRegex)) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        } else if(role.equals(Role.ANONYMOUS)) {
            throw new IllegalArgumentException("올바른 역할이 아닙니다.");
        }
    }
}
