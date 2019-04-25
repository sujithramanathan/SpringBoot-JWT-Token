package com.reports.security;

public class SecurityConstants {
    public static final String SECRET = "zyxtsrWvUqpOnmihGf";
    public static final long EXPIRATION_TIME = 6000000; // 10 Mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/v1/auth/signup/*";
    public static final String SIGN_IN_URL = "/v1/auth/token";
}
