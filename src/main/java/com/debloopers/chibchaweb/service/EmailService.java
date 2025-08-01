package com.debloopers.chibchaweb.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void enviarCorreoActivacion(String to, String subject, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);

        String htmlContent = """
    <html>
        <body>
            <h2>Bienvenido a ChibchaWeb</h2>
            <p>Gracias por registrarte. Para comenzar a disfrutar de nuestros servicios, necesitas activar tu cuenta.</p>
            <p>Haz clic <a href='http://localhost:8080/api/auth/activar?token=%s'>aquí</a> para verificar tu correo y activar tu cuenta.</p>
            <p>Si no has solicitado esta cuenta, puedes ignorar este mensaje.</p>
            <br/>
            <p>¡Gracias por confiar en nosotros!</p>
            <img src='cid:logo' alt='Logo' width='200'/>
        </body>
    </html>
    """.formatted(token);

        helper.setText(htmlContent, true);

        FileSystemResource image = new FileSystemResource(new File("src/main/java/com/debloopers/chibchaweb/util/Logo_ChibchaWeb.png"));
        helper.addInline("logo", image, "image/png");

        javaMailSender.send(message);
    }
}