package com.snapp.boxdemo.service;

public interface MailSenderService {
    void sendMail(String to, String subject, String text);
}
