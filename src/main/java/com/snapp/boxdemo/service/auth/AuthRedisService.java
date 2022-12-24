package com.snapp.boxdemo.service.auth;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface AuthRedisService {
    Optional<Authentication> authenticate(HttpServletRequest request);
}
