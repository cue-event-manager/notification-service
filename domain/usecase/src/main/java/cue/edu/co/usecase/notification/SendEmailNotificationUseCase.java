package cue.edu.co.usecase.notification;

import cue.edu.co.model.common.Event;
import cue.edu.co.model.notification.gateways.EmailNotificationSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendEmailNotificationUseCase {
    private final EmailNotificationSender notificationSender;

    public void execute(Event event) {
        notificationSender.send(event);
    }
}
