package com.gildedrose.factory;

import com.gildedrose.model.ItemType;
import com.gildedrose.service.RuleEngine;
import com.gildedrose.strategy.DynamicItemStrategy;
import com.gildedrose.strategy.ItemUpdateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemStrategyFactory {
    
    @Autowired
    private RuleEngine ruleEngine;

    public ItemUpdateStrategy createStrategy(ItemType itemType) {
        return new DynamicItemStrategy(ruleEngine, itemType.getRules(), itemType.getName());
    }
}