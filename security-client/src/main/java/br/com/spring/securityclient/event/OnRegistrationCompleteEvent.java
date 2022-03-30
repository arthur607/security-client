package br.com.spring.securityclient.event;

import br.com.spring.securityclient.dto.NotificationEmail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter @Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final NotificationEmail notificationEmail;

    public OnRegistrationCompleteEvent(NotificationEmail email) {
        super(email);
        this.notificationEmail = email;
    }
}
