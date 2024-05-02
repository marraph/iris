package com.marraph.iris.controller;

import com.marraph.iris.model.task.Topic;
import com.marraph.iris.service.task.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/topic")
@RestController
public final class TopicController extends AbstractController<Topic> {

    @Autowired
    public TopicController(TopicService topicService) {
        super(topicService);
    }
}