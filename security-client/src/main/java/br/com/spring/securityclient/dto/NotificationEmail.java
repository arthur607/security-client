package br.com.spring.securityclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class NotificationEmail {

    private String subject;
    private String recipient;
    private String body;

    public NotificationEmail() {
    }
}
