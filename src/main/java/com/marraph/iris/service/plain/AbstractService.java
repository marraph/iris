package com.marraph.iris.service.plain;

import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AbstractService <T> {

    @Async
    CompletableFuture<T> create(T entity);

    @Async
    CompletableFuture<T> update(Long id, T updatedEntity);

    @Async
    CompletableFuture<Optional<T>> getById(Long id);

    void delete(Long id);

    @Async
    CompletableFuture<Boolean> exists(T entity);

}