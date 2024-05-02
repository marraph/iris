package com.marraph.iris.controller;

import com.marraph.iris.service.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

public abstract class AbstractController<T> {

    private final AbstractService<T> service;

    AbstractController(AbstractService<T> service) {
        this.service = service;
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<T>> createEntity(T entity) {
        return service.create(entity).thenApply(ResponseEntity::ok);
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<ResponseEntity<T>> updateEntity(@PathVariable Long id, T entity) {
        return service.update(id, entity).thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<T>> getEntityById(@PathVariable Long id) {
        return service.getById(id)
                .thenApply(opt -> opt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteEntity(@PathVariable Long id) {
        return service.delete(id).thenApply(deleted -> deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build());
    }

    @GetMapping("/exists")
    public CompletableFuture<ResponseEntity<Boolean>> entityExists(T entity) {
        return service.exists(entity).thenApply(ResponseEntity::ok);
    }

}