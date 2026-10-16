package ru.hse.vyshkat.model;

public final class Bicycle extends Vehicle {
    public Bicycle(String inventoryNumber, int beginnerEase, int conditionScore) {
        super(inventoryNumber, beginnerEase, conditionScore);
    }

    @Override
    public String name() {
        return "Велосипед";
    }
}
