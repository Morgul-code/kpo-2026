package ru.hse.vyshkat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hse.vyshkat.model.Bicycle;
import ru.hse.vyshkat.model.Helmet;
import ru.hse.vyshkat.model.Scooter;
import ru.hse.vyshkat.service.FleetReportService;
import ru.hse.vyshkat.service.FleetService;
import ru.hse.vyshkat.service.ServiceCenter;

class FleetServiceTest {
    private static AnnotationConfigApplicationContext testContext(ServiceCenter inspection) {
        var context = new AnnotationConfigApplicationContext();
        context.registerBean("testServiceCenter", ServiceCenter.class, () -> inspection,
                definition -> definition.setPrimary(true));
        context.register(ApplicationConfiguration.class);
        context.refresh();
        return context;
    }

    @Test
    void acceptedVehicleAppearsInFleet() {
        try (var context = testContext(vehicle -> true)) {
            FleetService service = context.getBean(FleetService.class);
            FleetReportService reports = context.getBean(FleetReportService.class);

            // The fake accepts even a vehicle rejected by the production inspector.
            assertTrue(service.registerVehicle(new Bicycle("V-1", 5, 1)));
            assertEquals(1, reports.vehicleCount());
        }
    }

    @Test
    void rejectedVehicleNeverAppearsInFleet() {
        try (var context = testContext(vehicle -> false)) {
            FleetService service = context.getBean(FleetService.class);
            FleetReportService reports = context.getBean(FleetReportService.class);

            // The fake rejects even a vehicle accepted by the production inspector.
            assertFalse(service.registerVehicle(new Scooter("V-2", 9, 10, 5)));
            assertEquals(0, reports.vehicleCount());
            assertEquals(0.0, reports.totalDailyEnergyKwh(), 1e-9);
        }
    }

    @Test
    void duplicatedInventoryNumberIsRejectedAcrossEquipmentAndVehicles() {
        try (var context = testContext(vehicle -> true)) {
            FleetService service = context.getBean(FleetService.class);
            service.registerEquipment(new Helmet("SAME"));

            assertThrows(IllegalArgumentException.class,
                    () -> service.registerVehicle(new Bicycle("SAME", 7, 8)));
            assertEquals(1, context.getBean(FleetReportService.class).inventoryCount());
        }
    }

    @Test
    void equipmentDoesNotGoThroughTechnicalInspection() {
        try (var context = testContext(vehicle -> {
            throw new AssertionError("Equipment must not be inspected");
        })) {
            context.getBean(FleetService.class).registerEquipment(new Helmet("E-1"));
            assertEquals(1, context.getBean(FleetReportService.class).inventoryCount());
        }
    }

    @Test
    void independentContextsHaveIndependentFleets() {
        try (var first = testContext(vehicle -> true);
                var second = testContext(vehicle -> true)) {
            first.getBean(FleetService.class).registerEquipment(new Helmet("E-2"));

            assertEquals(1, first.getBean(FleetReportService.class).inventoryCount());
            assertEquals(0, second.getBean(FleetReportService.class).inventoryCount());
        }
    }
}
