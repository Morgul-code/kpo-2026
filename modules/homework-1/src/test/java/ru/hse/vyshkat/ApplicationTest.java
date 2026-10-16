package ru.hse.vyshkat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.hse.vyshkat.service.FleetReportService;

class ApplicationTest {
    @Test
    void springBootRunsConsoleScenarioWithProductionDependencies() {
        try (var context = new SpringApplicationBuilder(Main.class)
                .web(WebApplicationType.NONE)
                .properties("spring.main.banner-mode=off", "logging.level.root=OFF")
                .run()) {
            FleetReportService reports = context.getBean(FleetReportService.class);

            assertEquals(3, reports.vehicleCount());
            assertEquals(5, reports.inventoryCount());
            assertEquals(2.9, reports.totalDailyEnergyKwh(), 1e-9);
            assertEquals(2, reports.beginnerFriendlyVehicles().size());
        }
    }
}
