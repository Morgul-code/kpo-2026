package ru.hse.vyshkat;

import static org.junit.jupiter.api.Assertions.*;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import org.junit.jupiter.api.Test;
import ru.hse.vyshkat.model.Bicycle;
import ru.hse.vyshkat.model.Helmet;
import ru.hse.vyshkat.model.Scooter;
import ru.hse.vyshkat.service.Fleet;
import ru.hse.vyshkat.service.FleetReportService;
import ru.hse.vyshkat.service.FleetService;
import ru.hse.vyshkat.service.ServiceCenter;

class FleetServiceTest {
    private static Injector testInjector(ServiceCenter inspection) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Fleet.class).in(Scopes.SINGLETON);
                bind(ServiceCenter.class).toInstance(inspection);
            }
        });
    }

    @Test
    void acceptedVehicleAppearsInFleet() {
        Injector injector = testInjector(vehicle -> true);
        FleetService service = injector.getInstance(FleetService.class);
        FleetReportService reports = injector.getInstance(FleetReportService.class);

        assertTrue(service.registerVehicle(new Bicycle("V-1", 5, 1)));
        assertEquals(1, reports.vehicleCount());
    }

    @Test
    void rejectedVehicleNeverAppearsInFleet() {
        Injector injector = testInjector(vehicle -> false);
        FleetService service = injector.getInstance(FleetService.class);
        FleetReportService reports = injector.getInstance(FleetReportService.class);

        assertFalse(service.registerVehicle(new Scooter("V-2", 9, 10, 5)));
        assertEquals(0, reports.vehicleCount());
        assertEquals(0.0, reports.totalDailyEnergyKwh(), 1e-9);
    }

    @Test
    void duplicatedInventoryNumberIsRejectedAcrossEquipmentAndVehicles() {
        Injector injector = testInjector(vehicle -> true);
        FleetService service = injector.getInstance(FleetService.class);
        service.registerEquipment(new Helmet("SAME"));

        assertThrows(IllegalArgumentException.class,
                () -> service.registerVehicle(new Bicycle("SAME", 7, 8)));
        assertEquals(1, injector.getInstance(FleetReportService.class).inventoryCount());
    }

    @Test
    void equipmentDoesNotGoThroughTechnicalInspection() {
        Injector injector = testInjector(vehicle -> {
            throw new AssertionError("Equipment must not be inspected");
        });
        injector.getInstance(FleetService.class).registerEquipment(new Helmet("E-1"));
        assertEquals(1, injector.getInstance(FleetReportService.class).inventoryCount());
    }

    @Test
    void independentInjectorsHaveIndependentFleets() {
        Injector first = testInjector(vehicle -> true);
        Injector second = testInjector(vehicle -> true);
        first.getInstance(FleetService.class).registerEquipment(new Helmet("E-2"));

        assertEquals(1, first.getInstance(FleetReportService.class).inventoryCount());
        assertEquals(0, second.getInstance(FleetReportService.class).inventoryCount());
    }
}
