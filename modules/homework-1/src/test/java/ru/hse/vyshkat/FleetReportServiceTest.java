package ru.hse.vyshkat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.hse.vyshkat.model.Bicycle;
import ru.hse.vyshkat.model.ChargingStation;
import ru.hse.vyshkat.model.ElectricBicycle;
import ru.hse.vyshkat.model.Helmet;
import ru.hse.vyshkat.model.Scooter;
import ru.hse.vyshkat.service.Fleet;
import ru.hse.vyshkat.service.FleetReportService;

class FleetReportServiceTest {
    @Test
    void beginnerThresholdIsInclusiveAndOnlyVehiclesAppear() {
        Fleet fleet = new Fleet();
        fleet.add(new Bicycle("A", 5, 8));
        fleet.add(new Bicycle("B", 6, 8));
        fleet.add(new Scooter("C", 10, 8, 1.0));
        fleet.add(new Helmet("H"));
        var eligible = new FleetReportService(fleet).beginnerFriendlyVehicles();

        assertEquals(2, eligible.size());
        assertEquals("B", eligible.get(0).inventoryNumber());
        assertEquals("C", eligible.get(1).inventoryNumber());
    }

    @Test
    void sumsOnlyEnergyConsumersIncludingChargingEquipment() {
        Fleet fleet = new Fleet();
        fleet.add(new Scooter("S", 8, 9, 1.5));
        fleet.add(new ElectricBicycle("E", 8, 9, 0.8));
        fleet.add(new ChargingStation("C", 0.6));
        fleet.add(new Bicycle("B", 8, 9));
        fleet.add(new Helmet("H"));
        FleetReportService reports = new FleetReportService(fleet);

        assertEquals(2.9, reports.totalDailyEnergyKwh(), 1e-9);
        assertEquals(3, reports.vehicleCount());
        assertEquals(5, reports.inventoryCount());
        assertEquals(5, reports.inventory().size());
    }

    @Test
    void emptyFleetProducesEmptyReports() {
        FleetReportService reports = new FleetReportService(new Fleet());
        assertEquals(0, reports.vehicleCount());
        assertEquals(0, reports.inventoryCount());
        assertEquals(0, reports.totalDailyEnergyKwh());
        assertTrue(reports.beginnerFriendlyVehicles().isEmpty());
        assertTrue(reports.inventory().isEmpty());
    }
}
