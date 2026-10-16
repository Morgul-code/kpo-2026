package ru.hse.vyshkat.model;

public abstract class Equipment implements InventoryItem {
    private final String inventoryNumber;

    protected Equipment(String inventoryNumber) {
        if (inventoryNumber == null || inventoryNumber.isBlank()) {
            throw new IllegalArgumentException("Inventory number must not be blank");
        }
        this.inventoryNumber = inventoryNumber;
    }

    @Override
    public final String inventoryNumber() {
        return inventoryNumber;
    }
}
