package com.snapp.boxdemo.controller;

import com.snapp.boxdemo.repository.ClientRepository;
import com.snapp.boxdemo.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
@Slf4j
public class MailController {

    @Value("${mail.otp.subject}")
    private String subject;

    private MailSenderService mailSenderService;

    private final SecureRandom secureRandom;


    private final ClientRepository clientRepository;

    private final RedisTemplate<String, String> redisTemplate;

    @GetMapping("/otp")
    public void sendOtp(@RequestParam String to) {
        String otp = String.valueOf(secureRandom.nextInt(999999));
        redisTemplate.opsForValue().set(to, otp);
//        mailSenderService.sendMail(to, subject, otp);
        log.info("otp: {}", otp);
    }
}
