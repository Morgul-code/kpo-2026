package ru.hse.vyshkat.service;

import ru.hse.vyshkat.model.Vehicle;

@FunctionalInterface
public interface ServiceCenter {
    boolean inspect(Vehicle vehicle);
}
