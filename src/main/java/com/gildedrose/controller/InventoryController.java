package com.gildedrose.controller;

import com.gildedrose.model.InventoryItem;
import com.gildedrose.model.DailySnapshot;
import com.gildedrose.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {
    
    @Autowired
    private InventoryService inventoryService;
    
    // Get all inventory items
    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> items = inventoryService.getAllInventoryItems();
        return ResponseEntity.ok(items);
    }
    
    // Get inventory item by ID
    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getInventoryItemById(@PathVariable String id) {
        Optional<InventoryItem> item = inventoryService.getInventoryItemById(id);
        return item.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    // Create new inventory item
    @PostMapping
    public ResponseEntity<InventoryItem> createInventoryItem(@RequestBody InventoryItem item) {
        try {
            InventoryItem createdItem = inventoryService.createInventoryItem(item);
            return ResponseEntity.ok(createdItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Delete inventory item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable String id) {
        try {
            inventoryService.deleteInventoryItem(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Get inventory for specific date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<DailySnapshot>> getInventoryForDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<DailySnapshot> snapshots = inventoryService.getInventoryForDate(date);
            return ResponseEntity.ok(snapshots);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Simulate inventory to a specific date
    @PostMapping("/simulate-to/{date}")
    public ResponseEntity<String> simulateToDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            inventoryService.simulateToDate(date);
            return ResponseEntity.ok("Simulation completed for date: " + date);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error simulating: " + e.getMessage());
        }
    }
    
    // Advance one day
    @PostMapping("/advance-day")
    public ResponseEntity<String> advanceOneDay() {
        try {
            inventoryService.advanceOneDay();
            return ResponseEntity.ok("Advanced one day successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error advancing day: " + e.getMessage());
        }
    }
}

