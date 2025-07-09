package com.gildedrose.repository;

import com.gildedrose.model.InventoryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InventoryItemRepository extends MongoRepository<InventoryItem, String> {
    List<InventoryItem> findByCreatedDate(LocalDate date);
    List<InventoryItem> findByItemTypeId(String itemTypeId);
    List<InventoryItem> findByNameContainingIgnoreCase(String name);
}

