package ru.hse.vyshkat.service;

import com.google.inject.Inject;
import java.util.List;
import java.util.Objects;
import ru.hse.vyshkat.model.EnergyConsumer;
import ru.hse.vyshkat.model.InventoryItem;
import ru.hse.vyshkat.model.Vehicle;

/** Read-only reports about the accepted fleet and registered equipment. */
public final class FleetReportService {
    private final Fleet fleet;

    @Inject
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
        return fleet.allItems().stream()
                .filter(EnergyConsumer.class::isInstance)
                .map(EnergyConsumer.class::cast)
                .mapToDouble(EnergyConsumer::dailyEnergyKwh)
                .sum();
    }

    public List<Vehicle> beginnerFriendlyVehicles() {
        return fleet.vehicles().stream()
                .filter(vehicle -> vehicle.beginnerEase() >= 6)
                .toList();
    }

    public List<InventoryItem> inventory() {
        return fleet.allItems();
    }
}
