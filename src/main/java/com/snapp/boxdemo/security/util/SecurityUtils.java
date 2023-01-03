package com.snapp.boxdemo.security.util;

import com.snapp.boxdemo.model.security.ClientUserDetails;
import com.snapp.boxdemo.security.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityUtils {

    private final MessageSource source;

    private final SecureRandom secureRandom;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${otp.expiration}")
    private Integer otpExpiration;

    private boolean hasRole(Collection<? extends GrantedAuthority> grantedAuthorities, Role role) {
        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            if (grantedAuthority.getAuthority().equals(role.name()))
                return true;
        }
        return false;
    }

    public void checkOwner(Long ownerId, Locale locale) {
        ClientUserDetails principal = (ClientUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!hasRole(principal.getAuthorities(), Role.ROLE_ADMIN) && !Objects.isNull(ownerId) &&
                !principal.getUsername().equals(ownerId.toString())) {
            throw new AccessDeniedException(source.getMessage("access.denied.ownerId", null, locale));
            // or ownerId = principal.getUsername()
        }
    }

    public String generateOtp(String key) {
        String otp = String.format("%06d", secureRandom.nextInt(999999));
        redisTemplate.opsForValue().set(key, otp, otpExpiration, TimeUnit.MINUTES);
        log.info("OTP: {}", otp);
        return otp;
    }

}
