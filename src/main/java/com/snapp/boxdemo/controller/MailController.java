package com.snapp.boxdemo.controller;

import com.snapp.boxdemo.model.dto.BaseResponseDto;
import com.snapp.boxdemo.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
@Slf4j
public class MailController {
    @Value("mail.otp.subject")
    private String subject;

    private final SecureRandom random = new SecureRandom();

    private final RedisTemplate<String, String> redisTemplate;

    private final MessageSource messageSource;

    private final MailSenderService mailSenderService;

    @GetMapping("/auth/otp")
    public ResponseEntity<BaseResponseDto<Object>> sendOtpMail(
            @RequestParam String mail,
            Locale locale
    ) {
        String otp = String.valueOf(random.nextInt(999999));
        redisTemplate.opsForValue().set(otp, mail);
//        mailSenderService.sendMail(mail, subject, otp);
        log.info("otp: {}", otp);
        return ResponseEntity.ok().body(BaseResponseDto.<Object>builder().message(messageSource.getMessage(
                "mail.otp.success", null, locale
        )).build());
    }


}
