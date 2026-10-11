package ru.hse.vyshkat.model;

public abstract class Vehicle implements InventoryItem {
    private final String inventoryNumber;
    private final int beginnerEase;
    private final int conditionScore;

    protected Vehicle(String inventoryNumber, int beginnerEase, int conditionScore) {
        if (inventoryNumber == null || inventoryNumber.isBlank()) {
            throw new IllegalArgumentException("Inventory number must not be blank");
        }
        if (beginnerEase < 1 || beginnerEase > 10) {
            throw new IllegalArgumentException("Beginner ease must be between 1 and 10");
        }
        if (conditionScore < 1 || conditionScore > 10) {
            throw new IllegalArgumentException("Condition score must be between 1 and 10");
        }
        this.inventoryNumber = inventoryNumber;
        this.beginnerEase = beginnerEase;
        this.conditionScore = conditionScore;
    }

    @Override
    public final String inventoryNumber() {
        return inventoryNumber;
    }

    public final int beginnerEase() {
        return beginnerEase;
    }

    public final int conditionScore() {
        return conditionScore;
    }
}
