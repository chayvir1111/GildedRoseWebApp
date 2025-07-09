package com.gildedrose.model;

public class ItemRule {
    private String ruleName;
    private String ruleValue;
    private String conditionExpression;
    private int priority = 0;
    private boolean active = true;

    // Constructors
    public ItemRule() {}

    public ItemRule(String ruleName, String ruleValue, String conditionExpression) {
        this.ruleName = ruleName;
        this.ruleValue = ruleValue;
        this.conditionExpression = conditionExpression;
    }

    // Getters and Setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getRuleValue() { return ruleValue; }
    public void setRuleValue(String ruleValue) { this.ruleValue = ruleValue; }

    public String getConditionExpression() { return conditionExpression; }
    public void setConditionExpression(String conditionExpression) { this.conditionExpression = conditionExpression; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
