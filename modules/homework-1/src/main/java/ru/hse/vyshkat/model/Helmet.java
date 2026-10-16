package ru.hse.vyshkat.model;

public final class Helmet extends Equipment {
    public Helmet(String inventoryNumber) {
        super(inventoryNumber);
    }

    @Override
    public String name() {
        return "Шлем";
    }
}
