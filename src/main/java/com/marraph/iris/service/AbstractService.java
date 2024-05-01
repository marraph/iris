package com.marraph.iris.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AbstractService <T> {

    CompletableFuture<T> create(T entity);

    CompletableFuture<T> update(Long id, T updatedEntity);

    CompletableFuture<Optional<T>> getById(Long id);

    CompletableFuture<Boolean> delete(Long id);

    CompletableFuture<Boolean> exists(T entity);

}