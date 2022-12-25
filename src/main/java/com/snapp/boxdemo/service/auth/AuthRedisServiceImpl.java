package com.snapp.boxdemo.service.auth;

import com.snapp.boxdemo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
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

    private final RedisTemplate<String, String> redisTemplate;
    private final ClientService clientService;

    @Override
    public Optional<Authentication> authorization(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String mail = redisTemplate.opsForValue().get(authorization);
        if (Strings.isBlank(mail))
            throw new SecurityException("Not authorized!");
        Long clientId = clientService.getClientByMail(mail).getId();
        if (Objects.isNull(clientId))
            throw new SecurityException("Not authorized!");
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
        ADMIN
    }
}
