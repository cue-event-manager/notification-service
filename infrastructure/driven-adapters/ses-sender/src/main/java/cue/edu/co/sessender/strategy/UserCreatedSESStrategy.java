package cue.edu.co.sessender.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import cue.edu.co.model.common.Event;
import cue.edu.co.model.common.constants.EventType;
import cue.edu.co.model.notification.events.UserCreatedEvent;
import cue.edu.co.sessender.config.SESProperties;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;

import java.util.Map;

@Component
public class UserCreatedSESStrategy extends BaseSESTemplateStrategy<UserCreatedEvent>{
    private static final String TEMPLATE_ID = "WelcomeUserTemplate";

    public UserCreatedSESStrategy(SESProperties properties, ObjectMapper objectMapper) {
        super(properties, objectMapper);
    }

    @Override
    public String getEventType() {
        return EventType.USER_CREATED.getType();
    }

    @Override
    public SendTemplatedEmailRequest buildRequest(Event event) {
        UserCreatedEvent payload = parsePayload(event, UserCreatedEvent.class);

        return buildRequest(
                payload.getEmail(),
                TEMPLATE_ID,
                Map.of(
                        "name", payload.getFirstName()
                )
        );
    }
}
