package com.marraph.iris.controller;

import com.marraph.iris.model.task.Topic;
import com.marraph.iris.service.task.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(path = "/api/v1/topic")
@RestController
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Topic>> createTopic(@RequestBody Topic topic) {
        return topicService.create(topic).thenApply(ResponseEntity::ok);
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<ResponseEntity<Topic>> updateTopic(@PathVariable Long id, @RequestBody Topic topic) {
        return topicService.update(id, topic).thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Topic>> getTopicById(@PathVariable Long id) {
        return topicService.getById(id)
                .thenApply(opt -> opt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteTopic(@PathVariable Long id) {
        return topicService.delete(id).thenApply(deleted -> deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build());
    }

    @GetMapping("/exists")
    public CompletableFuture<ResponseEntity<Boolean>> topicExists(Topic topic) {
        return topicService.exists(topic).thenApply(ResponseEntity::ok);
    }
}