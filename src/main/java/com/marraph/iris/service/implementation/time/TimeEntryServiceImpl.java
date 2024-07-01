package com.marraph.iris.service.implementation.time;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.time.TimeEntry;
import com.marraph.iris.repository.TimeEntryRepository;
import com.marraph.iris.service.implementation.AbstractServiceImpl;
import com.marraph.iris.service.plain.time.TimeEntryService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public final class TimeEntryServiceImpl extends AbstractServiceImpl<TimeEntry> implements TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryServiceImpl(TimeEntryRepository timeEntryRepository) {
        super(timeEntryRepository, ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("endDate", ignoreCase())
                .withMatcher("startDate", ignoreCase())
        );

        this.timeEntryRepository = timeEntryRepository;
    }

    @Override
    public CompletableFuture<TimeEntry> create(TimeEntry timeEntry) {
        return this.exists(timeEntry).thenCompose(exists -> {

            if (exists) {
                final var found = timeEntryRepository.findOne(Example.of(timeEntry, getExampleMatcher()));
                if (found.isPresent()) return CompletableFuture.completedFuture(found.get());
            }

            timeEntry.setCreatedDate(LocalDateTime.now());
            timeEntry.setLastModifiedDate(LocalDateTime.now());
            return CompletableFuture.completedFuture(timeEntryRepository.save(timeEntry));
        });
    }

    @Override
    public CompletableFuture<TimeEntry> update(TimeEntry updatedEntity) {
        CompletableFuture<TimeEntry> future = new CompletableFuture<>();
        final var id = updatedEntity.getId();
        if (id == null) throw new IllegalArgumentException("ID is null");
        final var entry = timeEntryRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setComment(updatedEntity.getComment());
        entry.setStartDate(updatedEntity.getStartDate());
        entry.setEndDate(updatedEntity.getEndDate());
        entry.setLastModifiedDate(LocalDateTime.now());
        final var updatedEntry = timeEntryRepository.save(entry);

        future.complete(updatedEntry);
        return future;
    }

}
