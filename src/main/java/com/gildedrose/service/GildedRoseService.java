package com.gildedrose.service;

import com.gildedrose.factory.ItemStrategyFactory;
import com.gildedrose.model.Item;
import com.gildedrose.model.ItemType;
import com.gildedrose.strategy.ItemUpdateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GildedRoseService {
    
    @Autowired
    private ItemTypeService itemTypeService;
    
    @Autowired
    private ItemStrategyFactory strategyFactory;
    
    public void updateItems(Item[] items) {
        for (Item item : items) {
            updateSingleItem(item);
        }
    }
    
    public void updateSingleItem(Item item) {
        // Determine item type based on name
        ItemType itemType = determineItemType(item.name);
        
        if (itemType != null) {
            ItemUpdateStrategy strategy = strategyFactory.createStrategy(itemType);
            strategy.updateItem(item);
        } else {
            // Default to normal item behavior if type not found
            applyDefaultBehavior(item);
        }
    }
    
    private ItemType determineItemType(String itemName) {
        // First try exact match
        Optional<ItemType> exactMatch = itemTypeService.getItemTypeByName(itemName);
        if (exactMatch.isPresent()) {
            return exactMatch.get();
        }
        
        // Then try partial matches for common patterns
        String lowerName = itemName.toLowerCase();
        
        if (lowerName.contains("aged brie")) {
            return itemTypeService.getItemTypeByName("Aged Brie").orElse(null);
        }
        if (lowerName.contains("sulfuras")) {
            return itemTypeService.getItemTypeByName("Sulfuras").orElse(null);
        }
        if (lowerName.contains("backstage pass")) {
            return itemTypeService.getItemTypeByName("Backstage passes").orElse(null);
        }
        if (lowerName.contains("conjured")) {
            return itemTypeService.getItemTypeByName("Conjured").orElse(null);
        }
        
        // Default to Normal
        return itemTypeService.getItemTypeByName("Normal").orElse(null);
    }
    
    private void applyDefaultBehavior(Item item) {
        // Default normal item behavior
        item.sellIn--;
        if (item.quality > 0) {
            if (item.sellIn < 0) {
                item.quality = Math.max(0, item.quality - 2);
            } else {
                item.quality = Math.max(0, item.quality - 1);
            }
        }
    }
}