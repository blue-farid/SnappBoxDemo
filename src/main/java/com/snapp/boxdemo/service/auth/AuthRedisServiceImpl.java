package com.snapp.boxdemo.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AuthRedisServiceImpl implements AuthRedisService {

    private final RedisTemplate<String, Long> redisTemplate;

    @Override
    public Optional<Authentication> authenticate(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        Long clientId = redisTemplate.opsForValue().get(authorization);
        if (Objects.isNull(clientId)) {
            return Optional.empty();
        }
        return Optional.of(createAuthentication(clientId, Role.USER));
    }

    private Authentication createAuthentication(Long actor, @NonNull Role... roles) {
        // The difference between `hasAuthority` and `hasRole` is that the latter uses the `ROLE_` prefix
        List<GrantedAuthority> authorities = Stream.of(roles)
                .distinct()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(toList());
        return new UsernamePasswordAuthenticationToken(nonNull(actor) ? actor : "N/A", "N/A", authorities);
    }

    private enum Role {
        USER,
        ADMIN,
        SYSTEM,
    }
}
