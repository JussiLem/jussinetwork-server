package me.jussilemmetyinen.jussinetwork.bookmarks.registration.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

public class RegistrationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        
    }
}
