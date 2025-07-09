package com.gildedrose.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "inventory_items")
public class InventoryItem {
    @Id
    private String id;
    private String name;
    private String itemTypeId;
    private int initialQuality;
    private int initialSellIn;
    private LocalDate createdDate = LocalDate.now();
    private LocalDateTime createdAt = LocalDateTime.now();

    // Current state
    private int currentQuality;
    private int currentSellIn;

    // Constructors
    public InventoryItem() {}

    public InventoryItem(String name, String itemTypeId, int quality, int sellIn) {
        this.name = name;
        this.itemTypeId = itemTypeId;
        this.initialQuality = quality;
        this.initialSellIn = sellIn;
        this.currentQuality = quality;
        this.currentSellIn = sellIn;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getItemTypeId() { return itemTypeId; }
    public void setItemTypeId(String itemTypeId) { this.itemTypeId = itemTypeId; }

    public int getInitialQuality() { return initialQuality; }
    public void setInitialQuality(int initialQuality) { this.initialQuality = initialQuality; }

    public int getInitialSellIn() { return initialSellIn; }
    public void setInitialSellIn(int initialSellIn) { this.initialSellIn = initialSellIn; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getCurrentQuality() { return currentQuality; }
    public void setCurrentQuality(int currentQuality) { this.currentQuality = currentQuality; }

    public int getCurrentSellIn() { return currentSellIn; }
    public void setCurrentSellIn(int currentSellIn) { this.currentSellIn = currentSellIn; }
}

