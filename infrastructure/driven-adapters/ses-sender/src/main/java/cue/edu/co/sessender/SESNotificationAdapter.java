package cue.edu.co.sessender;

import cue.edu.co.model.common.Event;
import cue.edu.co.model.notification.gateways.EmailNotificationSender;
import cue.edu.co.sessender.strategy.SESTemplateStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;

import static cue.edu.co.sessender.constant.SESNotificationLog.EMAIL_SENT;
import static cue.edu.co.sessender.constant.SESNotificationLog.FAIL_EMAIL_SENT;

@Component
@RequiredArgsConstructor
@Log4j2
public class SESNotificationAdapter implements EmailNotificationSender {

    private final SESTemplateResolver templateResolver;

    private final SesClient sesClient;

    @Override
    public void send(Event event) {
        try {
            SESTemplateStrategy strategy = templateResolver.resolve(event.getType());
            SendTemplatedEmailRequest request = strategy.buildRequest(event);

            sesClient.sendTemplatedEmail(request);
            log.info(EMAIL_SENT.getMessage(), event.getType());
        } catch (Exception e) {
            log.error(FAIL_EMAIL_SENT.getMessage(), event.getType(), e);
            throw e;
        }
    }
}
