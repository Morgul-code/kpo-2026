package ru.hse.vyshkat.service;

import java.util.Objects;
import ru.hse.vyshkat.model.Vehicle;

/** A vehicle passes the technical inspection with a condition score of at least 6. */
public final class ConditionBasedServiceCenter implements ServiceCenter {
    @Override
    public boolean inspect(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "vehicle");
        return vehicle.conditionScore() >= 6;
    }
}
