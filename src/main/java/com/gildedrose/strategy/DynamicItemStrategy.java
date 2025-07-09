package com.gildedrose.strategy;

import com.gildedrose.model.Item;
import com.gildedrose.model.ItemRule;
import com.gildedrose.service.RuleEngine;
import java.util.List;

public class DynamicItemStrategy implements ItemUpdateStrategy {
    private final RuleEngine ruleEngine;
    private final List<ItemRule> rules;
    private final String strategyName;

    public DynamicItemStrategy(RuleEngine ruleEngine, List<ItemRule> rules, String strategyName) {
        this.ruleEngine = ruleEngine;
        this.rules = rules;
        this.strategyName = strategyName;
    }

    @Override
    public void updateItem(Item item) {
        ruleEngine.applyRules(item, rules);
    }

    @Override
    public String getStrategyName() {
        return strategyName;
    }
}