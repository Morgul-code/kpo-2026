package ru.hse.vyshkat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.hse.vyshkat.model.EnergyConsumer;
import ru.hse.vyshkat.model.InventoryItem;
import ru.hse.vyshkat.model.Vehicle;

/** Read-only reports about the accepted fleet and registered equipment. */
public final class FleetReportService {
    private final Fleet fleet;

    public FleetReportService(Fleet fleet) {
        this.fleet = Objects.requireNonNull(fleet, "fleet");
    }

    public int vehicleCount() {
        return fleet.vehicles().size();
    }

    public int inventoryCount() {
        return fleet.allItems().size();
    }

    public double totalDailyEnergyKwh() {
        double summaKwh = 0;
        for (InventoryItem item : fleet.allItems()) {
            if (item instanceof EnergyConsumer consumer) {
                summaKwh += consumer.dailyEnergyKwh();
            }
        }
        return summaKwh;
    }

    public List<Vehicle> beginnerFriendlyVehicles() {
        List<Vehicle> dlyaNovichkov = new ArrayList<>();
        for (Vehicle vehicle : fleet.vehicles()) {
            if (vehicle.beginnerEase() >= 6) {
                dlyaNovichkov.add(vehicle);
            }
        }
        return List.copyOf(dlyaNovichkov);
    }

    public List<InventoryItem> inventory() {
        return fleet.allItems();
    }
}
