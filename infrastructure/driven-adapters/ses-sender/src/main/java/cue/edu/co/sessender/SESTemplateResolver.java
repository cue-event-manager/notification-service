package cue.edu.co.sessender;
import cue.edu.co.sessender.strategy.SesTemplateStrategy;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class SESTemplateResolver {

    private final Map<String, SesTemplateStrategy> strategies = new HashMap<>();

    public SESTemplateResolver(List<SesTemplateStrategy> strategyList) {
        strategyList.forEach(strategy -> {
            strategies.put(strategy.getEventType(), strategy);
        });
    }

    public SesTemplateStrategy resolve(String eventType) {
        SesTemplateStrategy strategy = strategies.get(eventType);
        if (strategy == null) {
            throw new IllegalArgumentException("No SES strategy found for event type: " + eventType);
        }
        return strategy;
    }
}