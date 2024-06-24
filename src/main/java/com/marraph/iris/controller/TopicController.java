package com.marraph.iris.controller;

import com.marraph.iris.model.task.Topic;
import com.marraph.iris.model.wrapper.TopicCreation;
import com.marraph.iris.service.plain.task.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/topic")
@RestController
public final class TopicController extends AbstractController<Topic> {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        super(topicService);

        this.topicService = topicService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Topic>> createEntity(@RequestBody TopicCreation entity) {
        return this.topicService.create(entity.topic(), entity.teamId()).thenApply(ResponseEntity::ok);
    }
}