package com.snapp.boxdemo.controller;

import com.snapp.boxdemo.model.dto.BaseResponseDto;
import com.snapp.boxdemo.security.util.SecurityUtils;
import com.snapp.boxdemo.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    @Value("${mail.otp.subject}")
    private String subject;

    private final MailSenderService mailSenderService;

    private final SecurityUtils securityUtils;

    private final MessageSource source;

    @GetMapping("/otp")
    public ResponseEntity<BaseResponseDto<Object>> sendOtp(@RequestParam String to, Locale locale) {
        String otp = securityUtils.generateOtp(to);
        mailSenderService.sendMail(to, subject, otp);
        return ResponseEntity.ok().body(BaseResponseDto.<Object>builder().result(null).message(
                source.getMessage("mail.otp.success", null, locale)).build());
    }
}
