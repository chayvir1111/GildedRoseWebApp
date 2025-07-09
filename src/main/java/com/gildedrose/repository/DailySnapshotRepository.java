package com.gildedrose.repository;

import com.gildedrose.model.DailySnapshot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailySnapshotRepository extends MongoRepository<DailySnapshot, String> {
    List<DailySnapshot> findBySnapshotDate(LocalDate date);
    List<DailySnapshot> findByInventoryItemId(String inventoryItemId);
    Optional<DailySnapshot> findByInventoryItemIdAndSnapshotDate(String inventoryItemId, LocalDate date);
    List<DailySnapshot> findBySnapshotDateBetween(LocalDate startDate, LocalDate endDate);
}