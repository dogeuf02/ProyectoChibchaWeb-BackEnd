package com.debloopers.chibchaweb.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


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

    public void enviarCorreoSolicitudRegistro(String to, String nombreEmpresa) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Registro recibido para " + nombreEmpresa);

        String htmlTemplate = new String(
                getClass().getClassLoader().getResourceAsStream("template/registro_exitoso_es.html").readAllBytes(),
                StandardCharsets.UTF_8
        );

        String htmlContent = htmlTemplate.replace("{{nombreEmpresa}}", nombreEmpresa);
        helper.setText(htmlContent, true);

        FileSystemResource image = new FileSystemResource(new File("src/main/resources/image/Logo_ChibchaWeb.png"));
        helper.addInline("logo", image, "image/png");

        javaMailSender.send(message);
    }

    public void enviarCorreoRespuestaSolicitudRegistro(String to, String nombreEmpresa, String nuevoEstado) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);

        String asunto;
        String htmlTemplate;

        if ("ACTIVO".equalsIgnoreCase(nuevoEstado)) {
            asunto = "¡Tu solicitud ha sido aprobada!";
            htmlTemplate = new String(
                    getClass().getClassLoader().getResourceAsStream("template/registro_aprobado_es.html").readAllBytes(),
                    StandardCharsets.UTF_8
            );
        } else {
            asunto = "Tu solicitud ha sido rechazada";
            htmlTemplate = new String(
                    getClass().getClassLoader().getResourceAsStream("template/registro_rechazado_es.html").readAllBytes(),
                    StandardCharsets.UTF_8
            );
        }

        helper.setSubject(asunto);

        String htmlContent = htmlTemplate.replace("{{nombreEmpresa}}", nombreEmpresa);
        helper.setText(htmlContent, true);

        FileSystemResource image = new FileSystemResource(new File("src/main/resources/image/Logo_ChibchaWeb.png"));
        helper.addInline("logo", image, "image/png");

        javaMailSender.send(message);
    }
}