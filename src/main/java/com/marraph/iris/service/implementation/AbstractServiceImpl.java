package com.marraph.iris.service.implementation;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.service.plain.AbstractService;
import lombok.Getter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractServiceImpl<T> implements AbstractService<T> {

    private final JpaRepository<T, Long> repository;

    @Getter
    private final ExampleMatcher exampleMatcher;

    public AbstractServiceImpl(JpaRepository<T, Long> repository, ExampleMatcher exampleMatcher) {
        this.repository = repository;
        this.exampleMatcher = exampleMatcher;
    }

    @Override
    public CompletableFuture<T> getById(Long id) {
        return CompletableFuture.completedFuture(this.repository.findById(id).orElseThrow(()
                -> new EntryNotFoundException(id)));
    }

    @Override
    public CompletableFuture<List<T>> getAll() {
        return CompletableFuture.completedFuture(this.repository.findAll());
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public CompletableFuture<Boolean> exists(T entity) {
        return CompletableFuture.completedFuture(this.repository.exists(Example.of(entity, exampleMatcher)));
    }

}