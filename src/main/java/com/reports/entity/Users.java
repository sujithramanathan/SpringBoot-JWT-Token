package com.reports.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(value = "users")
public class Users implements UserDetails {

    private static final long serialVersionUID = 733871913764400552L;

    @Id
    private String rollNo;
    private String studentName;
    private String password;
    private String role;

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(this.role.split(",")).stream()
                .map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.rollNo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Student [rollNo=" + rollNo + ", studentName=" + studentName + ", password=" + password + ", role=" + role
                + "]";
    }

}
