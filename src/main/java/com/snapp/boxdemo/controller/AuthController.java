package com.snapp.boxdemo.controller;

import com.snapp.boxdemo.model.dto.BaseResponseDto;
import com.snapp.boxdemo.model.entity.Client;
import com.snapp.boxdemo.model.entity.RoleEntity;
import com.snapp.boxdemo.repository.ClientRepository;
import com.snapp.boxdemo.repository.RoleEntityRepository;
import com.snapp.boxdemo.security.Role;
import com.snapp.boxdemo.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Profile("!test")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    private final MessageSource messageSource;

    private final RedisTemplate<String, String> redisTemplate;

    private final ClientRepository clientRepository;

    private final RoleEntityRepository roleEntityRepository;

    @GetMapping("/login")
    public ResponseEntity<BaseResponseDto<Object>> login(
            @RequestParam String phoneNumber,
            @RequestParam String fullName,
            @RequestParam String mail,
            @RequestParam String password,
            Locale locale
    ) {
        try {
            if (!Objects.equals(redisTemplate.opsForValue().get(mail), password)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        BaseResponseDto.builder()
                                .message(messageSource.getMessage("login.failed", null, locale))
                                .build()
                );
            }
            Optional<Client> clientOptional = clientRepository.findByEmail(mail);
            Client client;
            if (clientOptional.isEmpty())
                client = signup(mail, fullName, phoneNumber);
            else
                client = clientOptional.get();

            client.setOneTimePassword(password);
            clientRepository.save(client);
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    client.getId(), password
                            )

                    );

            User user = new User(client.getId().toString(), password, authenticate.getAuthorities());

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtTokenUtil.generateToken(user)
                    )
                    .body(BaseResponseDto.<Object>builder().message(
                            messageSource.getMessage("login.success", null, locale)
                    ).build());
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private Client signup(String mail, String fullName, String phoneNumber) {
        Client client = new Client();
        client.setEmail(mail);
        client.setFullName(fullName);
        client.setPhoneNumber(phoneNumber);
        Set<RoleEntity> roleEntities = new HashSet<>();
        roleEntities.add(roleEntityRepository.findByName(Role.ROLE_USER));
        client.setRoles(roleEntities);
        return clientRepository.save(client);
    }
}
