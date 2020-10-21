package com.aws.codestar.projecttemplates.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties ("aws")
@NoArgsConstructor
@Data
public class AwsConfig {

    private String endpoint;
    private String sqs_name;
    private String default_region;

}
