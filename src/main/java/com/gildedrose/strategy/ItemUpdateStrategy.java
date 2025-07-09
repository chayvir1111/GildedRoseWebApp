package com.gildedrose.strategy;

import com.gildedrose.model.Item;

public interface ItemUpdateStrategy {
    void updateItem(Item item);
    String getStrategyName();
}

