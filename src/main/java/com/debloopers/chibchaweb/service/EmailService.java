package com.debloopers.chibchaweb.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void enviarCorreoActivacion(String to, String subject, String token) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);


        String htmlTemplate = new String(
                getClass().getClassLoader().getResourceAsStream("template/correo_activacion_es.html").readAllBytes(),
                StandardCharsets.UTF_8
        );

        String htmlContent = htmlTemplate.replace("{{token}}", token);

        helper.setText(htmlContent, true);

        FileSystemResource image = new FileSystemResource(new File("src/main/resources/image/Logo_ChibchaWeb.png"));
        helper.addInline("logo", image, "image/png");

        javaMailSender.send(message);
    }
}