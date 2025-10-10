package cue.edu.co.sessender.strategy;

import cue.edu.co.model.common.Event;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;

public interface SESTemplateStrategy {

    String getEventType();

    SendTemplatedEmailRequest buildRequest(Event event);
}
