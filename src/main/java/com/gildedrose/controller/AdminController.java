// src/main/java/com/gildedrose/controller/AdminController.java
package com.gildedrose.controller;

import com.gildedrose.model.ItemType;
import com.gildedrose.model.ItemRule;
import com.gildedrose.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private ItemTypeService itemTypeService;
    
    // Get all item types
    @GetMapping("/item-types")
    public ResponseEntity<List<ItemType>> getAllItemTypes() {
        List<ItemType> itemTypes = itemTypeService.getAllItemTypes();
        return ResponseEntity.ok(itemTypes);
    }
    
    // Get item type by ID
    @GetMapping("/item-types/{id}")
    public ResponseEntity<ItemType> getItemTypeById(@PathVariable String id) {
        Optional<ItemType> itemType = itemTypeService.getItemTypeById(id);
        return itemType.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    // Create new item type
    @PostMapping("/item-types")
    public ResponseEntity<ItemType> createItemType(@RequestBody ItemType itemType) {
        try {
            ItemType createdItemType = itemTypeService.createItemType(itemType);
            return ResponseEntity.ok(createdItemType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Update item type
    @PutMapping("/item-types/{id}")
    public ResponseEntity<ItemType> updateItemType(@PathVariable String id, @RequestBody ItemType itemType) {
        try {
            ItemType updatedItemType = itemTypeService.updateItemType(id, itemType);
            return ResponseEntity.ok(updatedItemType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Delete item type
    @DeleteMapping("/item-types/{id}")
    public ResponseEntity<Void> deleteItemType(@PathVariable String id) {
        try {
            itemTypeService.deleteItemType(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Add rule to item type
    @PostMapping("/item-types/{id}/rules")
    public ResponseEntity<ItemType> addRuleToItemType(@PathVariable String id, @RequestBody ItemRule rule) {
        try {
            ItemType updatedItemType = itemTypeService.addRuleToItemType(id, rule);
            return ResponseEntity.ok(updatedItemType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Initialize default data
    @PostMapping("/initialize")
    public ResponseEntity<String> initializeDefaultData() {
        try {
            itemTypeService.initializeDefaultItemTypes();
            return ResponseEntity.ok("Default item types initialized successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error initializing data: " + e.getMessage());
        }
    }
}

