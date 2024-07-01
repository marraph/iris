package com.marraph.iris.service.implementation.time;

import com.marraph.iris.exception.EntryNotFoundException;
import com.marraph.iris.model.time.Absence;
import com.marraph.iris.repository.AbsenceRepository;
import com.marraph.iris.service.implementation.AbstractServiceImpl;
import com.marraph.iris.service.plain.time.AbsenceService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public class AbsenceServiceImpl extends AbstractServiceImpl<Absence> implements AbsenceService {

    private final AbsenceRepository absenceRepository;

    public AbsenceServiceImpl(AbsenceRepository absenceRepository) {
        super(absenceRepository, ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("endDate", ignoreCase())
                .withMatcher("startDate", ignoreCase())
        );

        this.absenceRepository = absenceRepository;
    }

    @Override
    public CompletableFuture<Absence> create(Absence absence) {
        return this.exists(absence).thenCompose(exists -> {

            if (exists) {
                final var found = absenceRepository.findOne(Example.of(absence, getExampleMatcher()));
                if (found.isPresent()) return CompletableFuture.completedFuture(found.get());
            }

            absence.setCreatedDate(LocalDateTime.now());
            absence.setLastModifiedDate(LocalDateTime.now());
            return CompletableFuture.completedFuture(absenceRepository.save(absence));
        });
    }

    @Override
    public CompletableFuture<Absence> update(Absence updatedEntity) {
        CompletableFuture<Absence> future = new CompletableFuture<>();
        final var id = updatedEntity.getId();
        if (id == null) throw new IllegalArgumentException("ID is null");
        final var entry = absenceRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id));

        entry.setComment(updatedEntity.getComment());
        entry.setStartDate(updatedEntity.getStartDate());
        entry.setEndDate(updatedEntity.getEndDate());
        entry.setLastModifiedDate(LocalDateTime.now());
        final var updatedEntry = absenceRepository.save(entry);

        future.complete(updatedEntry);
        return future;
    }
}
