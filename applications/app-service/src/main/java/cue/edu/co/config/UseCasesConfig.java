package cue.edu.co.config;

import cue.edu.co.model.notification.gateways.EmailNotificationSender;
import cue.edu.co.usecase.notification.SendEmailNotificationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "cue.edu.co.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {
    @Bean
    public SendEmailNotificationUseCase sendEmailNotificationUseCase(EmailNotificationSender emailNotificationSender){
        return new SendEmailNotificationUseCase(emailNotificationSender);
    }
}
