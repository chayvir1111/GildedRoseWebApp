package com.gildedrose.service;

import com.gildedrose.factory.ItemStrategyFactory;
import com.gildedrose.model.*;
import com.gildedrose.repository.DailySnapshotRepository;
import com.gildedrose.repository.InventoryItemRepository;
import com.gildedrose.strategy.ItemUpdateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    
    @Autowired
    private InventoryItemRepository inventoryRepository;
    
    @Autowired
    private DailySnapshotRepository snapshotRepository;
    
    @Autowired
    private ItemTypeService itemTypeService;
    
    @Autowired
    private ItemStrategyFactory strategyFactory;
    
    public List<InventoryItem> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }
    
    public Optional<InventoryItem> getInventoryItemById(String id) {
        return inventoryRepository.findById(id);
    }
    
    public InventoryItem createInventoryItem(InventoryItem item) {
        return inventoryRepository.save(item);
    }
    
    public void deleteInventoryItem(String id) {
        inventoryRepository.deleteById(id);
    }
    
    public List<DailySnapshot> getInventoryForDate(LocalDate date) {
        return snapshotRepository.findBySnapshotDate(date);
    }
    
    public void simulateToDate(LocalDate targetDate) {
        LocalDate currentDate = LocalDate.now();
        
        while (currentDate.isBefore(targetDate) || currentDate.isEqual(targetDate)) {
            simulateDayForDate(currentDate);
            currentDate = currentDate.plusDays(1);
        }
    }
    
    private void simulateDayForDate(LocalDate date) {
        List<InventoryItem> allItems = inventoryRepository.findAll();
        
        for (InventoryItem inventoryItem : allItems) {
            // Check if snapshot already exists for this date
            Optional<DailySnapshot> existingSnapshot = snapshotRepository
                .findByInventoryItemIdAndSnapshotDate(inventoryItem.getId(), date);
                
            if (existingSnapshot.isEmpty()) {
                // Calculate item state for this date
                DailySnapshot snapshot = calculateItemStateForDate(inventoryItem, date);
                snapshotRepository.save(snapshot);
            }
        }
    }
    
    private DailySnapshot calculateItemStateForDate(InventoryItem inventoryItem, LocalDate targetDate) {
        // Get the item type and its rules
        Optional<ItemType> itemTypeOpt = itemTypeService.getItemTypeById(inventoryItem.getItemTypeId());
        if (itemTypeOpt.isEmpty()) {
            throw new RuntimeException("ItemType not found: " + inventoryItem.getItemTypeId());
        }
        
        ItemType itemType = itemTypeOpt.get();
        ItemUpdateStrategy strategy = strategyFactory.createStrategy(itemType);
        
        // Create a temporary item to simulate
        Item tempItem = new Item(
            inventoryItem.getName(),
            inventoryItem.getInitialSellIn(),
            inventoryItem.getInitialQuality()
        );
        
        // Calculate days from creation to target date
        long daysPassed = inventoryItem.getCreatedDate().until(targetDate).getDays();
        
        // Apply strategy for each day
        for (int i = 0; i < daysPassed; i++) {
            strategy.updateItem(tempItem);
        }
        
        return new DailySnapshot(
            inventoryItem.getId(),
            targetDate,
            tempItem.quality,
            tempItem.sellIn
        );
    }
    
    public void advanceOneDay() {
        LocalDate today = LocalDate.now();
        simulateDayForDate(today);
    }
}

