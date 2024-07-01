package com.marraph.iris.service.plain;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AbstractService <T> {

    @Async
    CompletableFuture<T> update(T updatedEntity);

    @Async
    CompletableFuture<T> getById(Long id);

    @Async
    CompletableFuture<List<T>> getAll();

    void delete(Long id);

    @Async
    CompletableFuture<Boolean> exists(T entity);

}