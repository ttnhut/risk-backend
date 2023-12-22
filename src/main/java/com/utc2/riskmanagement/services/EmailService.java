package com.utc2.riskmanagement.services;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendActivationEmail(String to, String urlButton, String title, String description, String name) throws MessagingException;
    void sendProgressRiskEmail(String to, String title, String status) throws MessagingException;
}
