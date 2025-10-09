package cue.edu.co.sqs.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import cue.edu.co.model.common.Event;
import cue.edu.co.sqs.listener.constant.SQSProcessorLog;
import cue.edu.co.usecase.notification.SendEmailNotificationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Log4j2
public class SQSProcessor implements Consumer<Message> {

    private final ObjectMapper mapper;
    private final SendEmailNotificationUseCase sendEmailNotificationUseCase;

    @Override
    public void accept(Message message) {
        try {
            Event event = mapper.readValue(message.body(), Event.class);
            log.info(SQSProcessorLog.EVENT_RECEIVED.getMessage(), event.getType());
            sendEmailNotificationUseCase.execute(event);
        } catch (Exception e) {
            log.error(SQSProcessorLog.ERROR_PROCESSING_MESSAGE.getMessage(), message.messageId(), e);
            throw new RuntimeException(e);
        }
    }
}
