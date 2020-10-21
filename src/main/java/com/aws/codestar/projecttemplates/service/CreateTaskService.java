package com.aws.codestar.projecttemplates.service;

import com.aws.codestar.projecttemplates.configuration.AwsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.net.URI;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateTaskService implements InitializingBean {

    private final AwsConfig awsConfig;
    private  SqsClient sqsClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        Region region = Region.of(awsConfig.getDefault_region());

        if (awsConfig.getEndpoint() != null) {
            sqsClient = SqsClient.builder()
                    .region(region)
                    .endpointOverride(URI.create(awsConfig.getEndpoint()))
                    .build();
        } else {
            sqsClient = SqsClient.builder()
                    .region(region)
                    .build();
        }

        ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder().queueNamePrefix(awsConfig.getSqs_name()).build();
        ListQueuesResponse listQueuesResponse = sqsClient.listQueues(listQueuesRequest);

        if (listQueuesResponse.queueUrls().size() == 0){
            CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                    .queueName(awsConfig.getSqs_name())
                    .build();
            sqsClient.createQueue(createQueueRequest);
        }
    }

    public String createTask() {
        GetQueueUrlResponse getQueueUrlResponse =
                sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(awsConfig.getSqs_name()).build());
        String queueUrl = getQueueUrlResponse.queueUrl();

        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody("Hello world! " + LocalDateTime.now().toString())
                .delaySeconds(10)
                .build());

        return "Exito!";
    }
}
