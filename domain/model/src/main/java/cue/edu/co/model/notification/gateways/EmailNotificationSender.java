package cue.edu.co.model.notification.gateways;

import cue.edu.co.model.common.Event;

public interface EmailNotificationSender {
    void send(Event event);
}
