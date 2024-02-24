package com.example.ingressbookstore.service;

import com.example.ingressbookstore.entity.AuthorEntity;
import com.example.ingressbookstore.repository.NameAndEmailProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String senderMail;


    public void sendEmail(NameAndEmailProjection subscribedStudent, AuthorEntity author, String bookName) {
        String message = "\nDear " + subscribedStudent.getName() + "\n Author " + author.getName() + " published " + "'" + bookName + "'" + " book";
        System.out.println(message);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderMail);
        mailMessage.setTo(subscribedStudent.getEmail());
        mailMessage.setSubject("New Book from "+author.getName()+"!!!");
        mailMessage.setText(message);
        emailSender.send(mailMessage);
        System.out.println("Message SENT\n"+message);
    }
}
