package com.gildedrose.service;

import com.gildedrose.model.Item;
import com.gildedrose.model.ItemRule;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RuleEngine {

    public void applyRules(Item item, List<ItemRule> rules) {
        // Sort rules by priority
        rules.stream()
            .filter(ItemRule::isActive)
            .sorted((r1, r2) -> Integer.compare(r1.getPriority(), r2.getPriority()))
            .forEach(rule -> applyRule(item, rule));
    }

    private void applyRule(Item item, ItemRule rule) {
        if (evaluateCondition(item, rule.getConditionExpression())) {
            executeRuleAction(item, rule);
        }
    }

    private boolean evaluateCondition(Item item, String condition) {
        if (condition == null || condition.trim().isEmpty()) {
            return true; // Default to true if no condition
        }

        try {
            // Simple condition parser - replace with proper expression evaluator for production
            String normalizedCondition = condition
                .replace("sellIn", String.valueOf(item.sellIn))
                .replace("quality", String.valueOf(item.quality));

            return evaluateSimpleCondition(normalizedCondition);
        } catch (Exception e) {
            System.err.println("Error evaluating condition: " + condition + " - " + e.getMessage());
            return false;
        }
    }

    private boolean evaluateSimpleCondition(String condition) {
        // Basic condition evaluator - handles simple comparisons
        // For production, consider using Spring Expression Language (SpEL) or similar
        
        if (condition.contains(" AND ")) {
            String[] parts = condition.split(" AND ");
            return evaluateSimpleCondition(parts[0].trim()) && evaluateSimpleCondition(parts[1].trim());
        }
        
        if (condition.contains(" OR ")) {
            String[] parts = condition.split(" OR ");
            return evaluateSimpleCondition(parts[0].trim()) || evaluateSimpleCondition(parts[1].trim());
        }

        // Handle simple comparisons
        if (condition.contains(">=")) {
            String[] parts = condition.split(">=");
            int left = Integer.parseInt(parts[0].trim());
            int right = Integer.parseInt(parts[1].trim());
            return left >= right;
        }
        
        if (condition.contains("<=")) {
            String[] parts = condition.split("<=");
            int left = Integer.parseInt(parts[0].trim());
            int right = Integer.parseInt(parts[1].trim());
            return left <= right;
        }
        
        if (condition.contains(">")) {
            String[] parts = condition.split(">");
            int left = Integer.parseInt(parts[0].trim());
            int right = Integer.parseInt(parts[1].trim());
            return left > right;
        }
        
        if (condition.contains("<")) {
            String[] parts = condition.split("<");
            int left = Integer.parseInt(parts[0].trim());
            int right = Integer.parseInt(parts[1].trim());
            return left < right;
        }
        
        if (condition.contains("==")) {
            String[] parts = condition.split("==");
            int left = Integer.parseInt(parts[0].trim());
            int right = Integer.parseInt(parts[1].trim());
            return left == right;
        }

        return true; // Default case
    }

    private void executeRuleAction(Item item, ItemRule rule) {
        String ruleName = rule.getRuleName();
        String ruleValue = rule.getRuleValue();

        try {
            switch (ruleName.toLowerCase()) {
                case "quality_change":
                case "daily_quality_change":
                case "quality_decrease":
                case "quality_increase":
                    int qualityChange = Integer.parseInt(ruleValue);
                    item.quality = Math.max(0, Math.min(50, item.quality + qualityChange));
                    break;
                    
                case "sellin_change":
                case "daily_sellin_change":
                    int sellInChange = Integer.parseInt(ruleValue);
                    item.sellIn += sellInChange;
                    break;
                    
                case "set_quality":
                    int newQuality = Integer.parseInt(ruleValue);
                    item.quality = Math.max(0, Math.min(50, newQuality));
                    break;
                    
                case "multiply_quality_change":
                    int multiplier = Integer.parseInt(ruleValue);
                    // This assumes we're multiplying the standard -1 change
                    int change = -1 * multiplier;
                    item.quality = Math.max(0, item.quality + change);
                    break;
                    
                default:
                    System.out.println("Unknown rule: " + ruleName);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid rule value: " + ruleValue + " for rule: " + ruleName);
        }
        
        // Always decrease sellIn by 1 (standard Gilded Rose behavior)
        if (!ruleName.toLowerCase().contains("sellin")) {
            item.sellIn--;
        }
    }
}