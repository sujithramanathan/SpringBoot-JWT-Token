package com.reports.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reports.entity.Users;
import com.reports.security.JwtTokenProvider;
import com.reports.service.ReportService;
import com.reports.util.Constants;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ReportService reportService;

    @PostMapping("/token")
    public ResponseEntity<Object> signin(@RequestHeader(value = "userId") String userId,
            @RequestHeader(value = "password") String password) {

        String token = "";
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, password));
            Users student = reportService.findByUserId(userId);
            if (null == student) {
                return new ResponseEntity<Object>("User not available ", HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
            }
            List<String> roles = Arrays.asList(student.getRole().split(","));
            token = jwtTokenProvider.createToken(userId, roles);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new BadCredentialsException("Invalid username/password supplied");
        }
        return new ResponseEntity<Object>(token, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('" + Constants.ADMIN_ROLE + "')")
    @PostMapping("/signup/{userName}")
    public ResponseEntity<String> signUp(@PathVariable(value = "userName") String userName, @RequestHeader(value = "userId") String userId,
            @RequestHeader(value = "password") String password, @RequestHeader(value = "role") String role) {

        Users users = new Users();
        users.setRollNo(userId);
        users.setStudentName(userName);
        users.setPassword(bCryptPasswordEncoder.encode(password));
        if (null == role || role.isEmpty()) {
            users.setRole("STUDENT");
        }
        else {
            users.setRole(role);
        }
        return reportService.saveStudent(users);
    }
}
