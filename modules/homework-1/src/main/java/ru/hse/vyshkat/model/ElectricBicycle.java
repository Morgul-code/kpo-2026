package ru.hse.vyshkat.model;

public final class ElectricBicycle extends Vehicle implements EnergyConsumer {
    private final double dailyEnergyKwh;

    public ElectricBicycle(String inventoryNumber, int beginnerEase, int conditionScore,
                           double dailyEnergyKwh) {
        super(inventoryNumber, beginnerEase, conditionScore);
        if (!Double.isFinite(dailyEnergyKwh) || dailyEnergyKwh < 0) {
            throw new IllegalArgumentException("Daily energy must be finite and nonnegative");
        }
        this.dailyEnergyKwh = dailyEnergyKwh;
    }

    @Override
    public String name() {
        return "Электровелосипед";
    }

    @Override
    public double dailyEnergyKwh() {
        return dailyEnergyKwh;
    }
}
