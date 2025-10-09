package cue.edu.co.sessender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.ses")
public record SESProperties(  String region,
                              String endpoint,
                              String sourceEmail){

}