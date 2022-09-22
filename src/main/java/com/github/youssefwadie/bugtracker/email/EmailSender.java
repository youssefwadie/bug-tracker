package com.github.youssefwadie.bugtracker.email;


public interface EmailSender {
    void send(String subject, String email, String to);
}
