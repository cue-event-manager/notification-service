package cue.edu.co.sessender.strategy;

import cue.edu.co.model.common.Event;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;

public interface SesTemplateStrategy{

    String getEventType();

    SendTemplatedEmailRequest buildRequest(Event event);
}
