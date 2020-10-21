package com.aws.codestar.projecttemplates.controller;

import com.aws.codestar.projecttemplates.service.CreateTaskService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class HelloWorldController {

    private final CreateTaskService createTaskService;

    @RequestMapping(method = RequestMethod.GET)
    public String helloWorld() {
        return createTaskService.createTask();
    }

}
