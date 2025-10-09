package cue.edu.co.sessender.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SESNotificationLog {

    EMAIL_SENT("Email sent for event:{}"),
    FAIL_EMAIL_SENT("Failed to send email for event {}");

    private final String message;
}
