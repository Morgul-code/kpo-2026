package ru.hse.vyshkat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hse.vyshkat.service.ConditionBasedServiceCenter;
import ru.hse.vyshkat.service.Fleet;
import ru.hse.vyshkat.service.FleetReportService;
import ru.hse.vyshkat.service.FleetService;
import ru.hse.vyshkat.service.ServiceCenter;

/** Spring bindings; services and domain objects remain ordinary Java classes. */
@Configuration(proxyBeanMethods = false)
public class ApplicationConfiguration {
    @Bean
    public Fleet fleet() {
        return new Fleet();
    }

    @Bean
    public ServiceCenter serviceCenter() {
        return new ConditionBasedServiceCenter();
    }

    @Bean
    public FleetService fleetService(Fleet fleet, ServiceCenter serviceCenter) {
        return new FleetService(fleet, serviceCenter);
    }

    @Bean
    public FleetReportService fleetReportService(Fleet fleet) {
        return new FleetReportService(fleet);
    }

    @Bean
    public ConsoleScenario consoleScenario(FleetService registration, FleetReportService reports) {
        return new ConsoleScenario(registration, reports);
    }
}
