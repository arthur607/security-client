package br.com.spring.securityclient.event;

import br.com.spring.securityclient.error.ExceptionMailSender;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final JavaMailSender mailSender;

    @Autowired
    public RegistrationListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(@NotNull OnRegistrationCompleteEvent event) {
    this.confirmRegistration(event);

    }
    private void confirmRegistration(OnRegistrationCompleteEvent event) {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("salsa-modas@gmail.com");
            messageHelper.setTo(event.getNotificationEmail().getRecipient());
            messageHelper.setReplyTo("arthurrodriguesg1@gmail.com");
            messageHelper.setSubject(event.getNotificationEmail().getSubject());
            messageHelper.setText(event.getNotificationEmail().getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new ExceptionMailSender("Exception occurred when sending mail to " + event.getNotificationEmail().getRecipient(), e);
        }
    }
}
