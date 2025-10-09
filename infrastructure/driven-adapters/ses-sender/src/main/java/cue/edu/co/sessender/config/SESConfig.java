package cue.edu.co.sessender.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.metrics.MetricPublisher;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.SesClientBuilder;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(SESProperties.class)
public class SESConfig {
    @Bean
    public SesClient sesClient(SESProperties properties, MetricPublisher metricPublisher) {
        SesClientBuilder builder = SesClient.builder()
                .region(Region.of(properties.region()))
                .overrideConfiguration(cfg -> cfg.addMetricPublisher(metricPublisher))
                .credentialsProvider(getProviderChain());

        URI endpoint = resolveEndpoint(properties);
        if (endpoint != null) {
            builder.endpointOverride(endpoint);
        }

        return builder.build();
    }

    private AwsCredentialsProviderChain getProviderChain() {
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .addCredentialsProvider(SystemPropertyCredentialsProvider.create())
                .addCredentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .addCredentialsProvider(ProfileCredentialsProvider.create())
                .addCredentialsProvider(ContainerCredentialsProvider.builder().build())
                .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
                .build();
    }

    private URI resolveEndpoint(SESProperties properties) {
        if (properties.endpoint() != null && !properties.endpoint().isEmpty()) {
            return URI.create(properties.endpoint());
        }
        return null;
    }
}
