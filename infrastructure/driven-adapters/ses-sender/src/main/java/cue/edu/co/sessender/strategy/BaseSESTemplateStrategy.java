package cue.edu.co.sessender.strategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import cue.edu.co.model.common.Event;
import cue.edu.co.sessender.config.SESProperties;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;

import java.util.Map;


@RequiredArgsConstructor
public abstract class BaseSESTemplateStrategy<T> implements SesTemplateStrategy {

    protected final SESProperties properties;
    protected final ObjectMapper objectMapper;

    protected T parsePayload(Event event, Class<T> clazz) {
        return objectMapper.convertValue(event.getPayload(), clazz);
    }


    protected String toJson(Map<String, Object> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error building SES template data", e);
        }
    }

    protected Destination destination(String email) {
        return Destination.builder().toAddresses(email).build();
    }

    protected SendTemplatedEmailRequest buildRequest(String email, String template, Map<String, Object> data) {
        return SendTemplatedEmailRequest.builder()
                .source(properties.sourceEmail())
                .destination(destination(email))
                .template(template)
                .templateData(toJson(data))
                .build();
    }
}