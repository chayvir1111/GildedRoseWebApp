package com.gildedrose.service;

import com.gildedrose.model.ItemType;
import com.gildedrose.model.ItemRule;
import com.gildedrose.repository.ItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ItemTypeService {
    
    @Autowired
    private ItemTypeRepository itemTypeRepository;
    
    public List<ItemType> getAllItemTypes() {
        return itemTypeRepository.findAll();
    }
    
    public Optional<ItemType> getItemTypeById(String id) {
        return itemTypeRepository.findById(id);
    }
    
    public Optional<ItemType> getItemTypeByName(String name) {
        return itemTypeRepository.findByName(name);
    }
    
    public ItemType createItemType(ItemType itemType) {
        itemType.setCreatedAt(LocalDateTime.now());
        itemType.setUpdatedAt(LocalDateTime.now());
        return itemTypeRepository.save(itemType);
    }
    
    public ItemType updateItemType(String id, ItemType itemType) {
        itemType.setId(id);
        itemType.setUpdatedAt(LocalDateTime.now());
        return itemTypeRepository.save(itemType);
    }
    
    public void deleteItemType(String id) {
        itemTypeRepository.deleteById(id);
    }
    
    public ItemType addRuleToItemType(String itemTypeId, ItemRule rule) {
        Optional<ItemType> itemTypeOpt = itemTypeRepository.findById(itemTypeId);
        if (itemTypeOpt.isPresent()) {
            ItemType itemType = itemTypeOpt.get();
            itemType.getRules().add(rule);
            itemType.setUpdatedAt(LocalDateTime.now());
            return itemTypeRepository.save(itemType);
        }
        throw new RuntimeException("ItemType not found: " + itemTypeId);
    }
    
    // Initialize default item types
    public void initializeDefaultItemTypes() {
        if (itemTypeRepository.count() == 0) {
            createDefaultItemTypes();
        }
    }
    
    private void createDefaultItemTypes() {
        // Normal Item
        ItemType normal = new ItemType("Normal", "Standard items that degrade normally");
        normal.getRules().add(new ItemRule("quality_change", "-1", "sellIn >= 0 AND quality > 0"));
        normal.getRules().add(new ItemRule("quality_change", "-2", "sellIn < 0 AND quality > 0"));
        itemTypeRepository.save(normal);
        
        // Aged Brie
        ItemType agedBrie = new ItemType("Aged Brie", "Gets better with age");
        agedBrie.getRules().add(new ItemRule("quality_change", "1", "sellIn >= 0 AND quality < 50"));
        agedBrie.getRules().add(new ItemRule("quality_change", "2", "sellIn < 0 AND quality < 50"));
        itemTypeRepository.save(agedBrie);
        
        // Sulfuras
        ItemType sulfuras = new ItemType("Sulfuras", "Legendary item that never changes");
        sulfuras.getRules().add(new ItemRule("set_quality", "80", ""));
        sulfuras.getRules().add(new ItemRule("sellin_change", "0", "")); // Don't change sellIn
        itemTypeRepository.save(sulfuras);
        
        // Backstage Pass
        ItemType backstage = new ItemType("Backstage passes", "Increase in value as concert approaches");
        backstage.getRules().add(new ItemRule("quality_change", "1", "sellIn > 10 AND quality < 50"));
        backstage.getRules().add(new ItemRule("quality_change", "2", "sellIn > 5 AND sellIn <= 10 AND quality < 50"));
        backstage.getRules().add(new ItemRule("quality_change", "3", "sellIn > 0 AND sellIn <= 5 AND quality < 50"));
        backstage.getRules().add(new ItemRule("set_quality", "0", "sellIn <= 0"));
        itemTypeRepository.save(backstage);
        
        // Conjured Item
        ItemType conjured = new ItemType("Conjured", "Degrade twice as fast as normal items");
        conjured.getRules().add(new ItemRule("quality_change", "-2", "sellIn >= 0 AND quality > 0"));
        conjured.getRules().add(new ItemRule("quality_change", "-4", "sellIn < 0 AND quality > 0"));
        itemTypeRepository.save(conjured);
    }
}

