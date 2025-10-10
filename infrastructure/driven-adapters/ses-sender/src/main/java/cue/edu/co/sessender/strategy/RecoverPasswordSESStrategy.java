package cue.edu.co.sessender.strategy;

import cue.edu.co.model.common.Event;
import cue.edu.co.model.common.constants.EventType;
import cue.edu.co.model.notification.events.RecoverPasswordEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import cue.edu.co.sessender.config.SESProperties;
import cue.edu.co.sessender.utils.DateFormatterUtil;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class RecoverPasswordSesStrategy extends BaseSESTemplateStrategy<RecoverPasswordEvent> {

    private static final String TEMPLATE_ID = "RecoverPasswordTemplate";

    public RecoverPasswordSesStrategy(SESProperties properties, ObjectMapper objectMapper) {
        super(properties, objectMapper);
    }

    @Override
    public String getEventType() {
        return EventType.RECOVER_PASSWORD.getType();
    }

    @Override
    public SendTemplatedEmailRequest buildRequest(Event event) {
        RecoverPasswordEvent payload = parsePayload(event, RecoverPasswordEvent.class);
        String expiration = DateFormatterUtil.formatDate(LocalDateTime.parse(payload.getExpirationTime()));

        return buildRequest(
                payload.getEmail(),
                TEMPLATE_ID,
                Map.of(
                        "name", payload.getName(),
                        "recoveryCode", payload.getRecoveryCode(),
                        "expirationTime", expiration
                )
        );
    }
}
