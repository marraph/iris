package com.marraph.iris.service.implementation.time;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.time.DailyEntry;
import com.marraph.iris.repository.DailyEntryRepository;
import com.marraph.iris.service.implementation.AbstractServiceImpl;
import com.marraph.iris.service.plain.time.DailyEntryService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class DailyEntryServiceImpl extends AbstractServiceImpl<DailyEntry> implements DailyEntryService {

    private final DailyEntryRepository dailyEntryRepository;

    public DailyEntryServiceImpl(DailyEntryRepository dailyEntryRepository) {
        super(dailyEntryRepository, ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("endDate", ignoreCase())
                .withMatcher("startDate", ignoreCase())
        );

        this.dailyEntryRepository = dailyEntryRepository;
    }

    @Override
    public CompletableFuture<DailyEntry> create(DailyEntry dailyEntry) {
        return this.exists(dailyEntry).thenCompose(exists -> {

            if (exists) {
                final var found = dailyEntryRepository.findOne(Example.of(dailyEntry, getExampleMatcher()));
                if (found.isPresent()) return CompletableFuture.completedFuture(found.get());
            }

            dailyEntry.setCreatedDate(LocalDateTime.now());
            dailyEntry.setLastModifiedDate(LocalDateTime.now());
            return CompletableFuture.completedFuture(dailyEntryRepository.save(dailyEntry));
        });
    }

    @Override
    public CompletableFuture<DailyEntry> update(DailyEntry updatedEntity) {
        CompletableFuture<DailyEntry> future = new CompletableFuture<>();
        final var id = updatedEntity.getId();
        if (id == null) throw new IllegalArgumentException("ID is null");
        final var entry = dailyEntryRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setComment(updatedEntity.getComment());
        entry.setStartDate(updatedEntity.getStartDate());
        entry.setEndDate(updatedEntity.getEndDate());
        entry.setLastModifiedDate(LocalDateTime.now());
        final var updatedEntry = dailyEntryRepository.save(entry);

        future.complete(updatedEntry);
        return future;
    }
}