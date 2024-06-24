package com.marraph.iris.controller;

import com.marraph.iris.model.task.Topic;
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
    public CompletableFuture<ResponseEntity<Topic>> createEntity(@RequestBody Topic entity, @RequestBody Long teamId) {
        return this.topicService.create(entity, teamId).thenApply(ResponseEntity::ok);
    }
}