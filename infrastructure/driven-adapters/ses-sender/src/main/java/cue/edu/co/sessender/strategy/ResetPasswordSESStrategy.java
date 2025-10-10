package cue.edu.co.sessender.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import cue.edu.co.model.common.Event;
import cue.edu.co.model.common.constants.EventType;
import cue.edu.co.model.notification.events.PasswordResetEvent;
import cue.edu.co.model.notification.events.RecoverPasswordEvent;
import cue.edu.co.sessender.config.SESProperties;
import cue.edu.co.sessender.utils.DateFormatterUtil;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class ResetPasswordSESStrategy extends BaseSESTemplateStrategy<PasswordResetEvent> {

    private static final String TEMPLATE_ID = "PasswordResetTemplate";

    public ResetPasswordSESStrategy(SESProperties properties, ObjectMapper objectMapper) {
        super(properties, objectMapper);
    }

    @Override
    public String getEventType() {
        return EventType.PASSWORD_RESET.getType();
    }

    @Override
    public SendTemplatedEmailRequest buildRequest(Event event) {
        PasswordResetEvent payload = parsePayload(event, PasswordResetEvent.class);
        String occurredAt = DateFormatterUtil.formatDate(LocalDateTime.parse(payload.getOccurredAt()));

        return buildRequest(
                payload.getEmail(),
                TEMPLATE_ID,
                Map.of(
                        "name", payload.getName(),
                        "occurredAt", occurredAt
                )
        );
    }
}
