package com.aws.codestar.projecttemplates.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;

import java.net.URI;

@RestController
public class HelloWorldController {
    @Value("${aws.local.endpoint}")
    String awsEndpoint;

    @Value("${aws.sqs.name}")
    String queueName;

    Region REGION = Region.US_EAST_1;

    @RequestMapping(method = RequestMethod.GET)
    public String helloWorld() {

        SqsClient sqsClient;

        if (awsEndpoint != null) {
            sqsClient = SqsClient.builder()
                    .region(REGION)
                    .endpointOverride(URI.create(awsEndpoint))
                    .build();
        } else {
            sqsClient = SqsClient.builder()
                    .region(REGION)
                    .build();
        }


        ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder()
                .queueNamePrefix(queueName).build();
        ListQueuesResponse listQueuesResponse = sqsClient.listQueues(listQueuesRequest);

        if (!listQueuesResponse.hasQueueUrls()) {
            CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                    .queueName(queueName)
                    .build();

            sqsClient.createQueue(createQueueRequest);
        }

        return listQueuesResponse.toString();
    }

}
