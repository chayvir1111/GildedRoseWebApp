// src/main/java/com/gildedrose/repository/ItemTypeRepository.java
package com.gildedrose.repository;

import com.gildedrose.model.ItemType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ItemTypeRepository extends MongoRepository<ItemType, String> {
    Optional<ItemType> findByName(String name);
    boolean existsByName(String name);
}
