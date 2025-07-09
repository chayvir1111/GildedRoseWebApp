package com.gildedrose.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "daily_snapshots")
public class DailySnapshot {
    @Id
    private String id;
    private String inventoryItemId;
    private LocalDate snapshotDate;
    private int quality;
    private int sellIn;
    private LocalDateTime calculatedAt = LocalDateTime.now();

    // Constructors
    public DailySnapshot() {}

    public DailySnapshot(String inventoryItemId, LocalDate snapshotDate, int quality, int sellIn) {
        this.inventoryItemId = inventoryItemId;
        this.snapshotDate = snapshotDate;
        this.quality = quality;
        this.sellIn = sellIn;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getInventoryItemId() { return inventoryItemId; }
    public void setInventoryItemId(String inventoryItemId) { this.inventoryItemId = inventoryItemId; }

    public LocalDate getSnapshotDate() { return snapshotDate; }
    public void setSnapshotDate(LocalDate snapshotDate) { this.snapshotDate = snapshotDate; }

    public int getQuality() { return quality; }
    public void setQuality(int quality) { this.quality = quality; }

    public int getSellIn() { return sellIn; }
    public void setSellIn(int sellIn) { this.sellIn = sellIn; }

    public LocalDateTime getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(LocalDateTime calculatedAt) { this.calculatedAt = calculatedAt; }
}