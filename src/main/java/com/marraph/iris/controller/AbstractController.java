package com.marraph.iris.controller;

import com.marraph.iris.service.plain.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractController<T> {

    private final AbstractService<T> service;

    AbstractController(AbstractService<T> service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<T>> createEntity(@RequestBody T entity) {
        return service.create(entity).thenApply(ResponseEntity::ok);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/update/{id}")
    public CompletableFuture<ResponseEntity<T>> updateEntity(@PathVariable Long id, @RequestBody T entity) {
        return service.update(id, entity).thenApply(ResponseEntity::ok);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get/{id}")
    public CompletableFuture<ResponseEntity<T>> getEntityById(@PathVariable Long id) {
        return service.getById(id)
                .thenApply(opt -> opt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get")
    public CompletableFuture<ResponseEntity<List<T>>> getAll() {
        return service.getAll().thenApply(ResponseEntity::ok);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete/{id}")
    public void deleteEntity(@PathVariable Long id) {
        service.delete(id);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/exists")
    public CompletableFuture<ResponseEntity<Boolean>> entityExists(@RequestBody T entity) {
        return service.exists(entity).thenApply(ResponseEntity::ok);
    }

}