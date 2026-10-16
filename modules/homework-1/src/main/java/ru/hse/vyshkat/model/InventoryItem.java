package ru.hse.vyshkat.model;

/** An object that is registered on the company's balance sheet. */
public interface InventoryItem {
    String inventoryNumber();

    String name();
}
