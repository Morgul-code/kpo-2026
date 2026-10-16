package ru.hse.vyshkat.model;

public final class ChargingStation extends Equipment implements EnergyConsumer {
    private final double dailyEnergyKwh;

    public ChargingStation(String inventoryNumber, double dailyEnergyKwh) {
        super(inventoryNumber);
        if (!Double.isFinite(dailyEnergyKwh) || dailyEnergyKwh < 0) {
            throw new IllegalArgumentException("Daily energy must be finite and nonnegative");
        }
        this.dailyEnergyKwh = dailyEnergyKwh;
    }

    @Override
    public String name() {
        return "Зарядная станция";
    }

    @Override
    public double dailyEnergyKwh() {
        return dailyEnergyKwh;
    }
}
