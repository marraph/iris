package com.marraph.iris.repository;

import com.marraph.iris.model.time.DailyEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyEntryRepository extends JpaRepository<DailyEntry, Long> {
}
