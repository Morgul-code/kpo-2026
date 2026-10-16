package ru.hse.vyshkat.service;

import java.util.Objects;
import ru.hse.vyshkat.model.Equipment;
import ru.hse.vyshkat.model.Vehicle;

/** Only approved vehicles are registered; equipment needs no inspection. */
public final class FleetService {
    private final Fleet fleet;
    private final ServiceCenter serviceCenter;

    public FleetService(Fleet fleet, ServiceCenter serviceCenter) {
        this.fleet = Objects.requireNonNull(fleet, "fleet");
        this.serviceCenter = Objects.requireNonNull(serviceCenter, "serviceCenter");
    }

    public boolean registerVehicle(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "vehicle");
        // Сначала проверяем технику, потом добавляем на баланс.
        boolean proshelTehosmotr = serviceCenter.inspect(vehicle);
        if (!proshelTehosmotr) {
            return false;
        }
        fleet.add(vehicle);
        return true;
    }

    public void registerEquipment(Equipment equipment) {
        fleet.add(Objects.requireNonNull(equipment, "equipment"));
    }
}
