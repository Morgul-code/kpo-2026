package ru.hse.vyshkat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.hse.vyshkat.model.Bicycle;
import ru.hse.vyshkat.model.ChargingStation;
import ru.hse.vyshkat.model.ElectricBicycle;
import ru.hse.vyshkat.model.Helmet;
import ru.hse.vyshkat.model.Scooter;
import ru.hse.vyshkat.service.FleetReportService;
import ru.hse.vyshkat.service.FleetService;

/** Demonstrates all use cases without interactive input. */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ApplicationModule());
        FleetService registration = injector.getInstance(FleetService.class);
        FleetReportService reports = injector.getInstance(FleetReportService.class);

        System.out.println("ВышКат — учёт парка");
        System.out.println("Самокат принят: "
                + registration.registerVehicle(new Scooter("V-001", 8, 9, 1.5)));
        System.out.println("Неисправный самокат принят: "
                + registration.registerVehicle(new Scooter("V-002", 9, 3, 2.0)));
        System.out.println("Велосипед принят: "
                + registration.registerVehicle(new Bicycle("V-003", 7, 7)));
        System.out.println("Сложный электровелосипед принят: "
                + registration.registerVehicle(new ElectricBicycle("V-004", 4, 8, 0.8)));

        registration.registerEquipment(new Helmet("E-001"));
        registration.registerEquipment(new ChargingStation("E-002", 0.6));

        System.out.println("Транспортных средств: " + reports.vehicleCount());
        System.out.println("Всего инвентарных объектов: " + reports.inventoryCount());
        System.out.printf("Энергия за сутки: %.2f кВт·ч%n", reports.totalDailyEnergyKwh());

        System.out.println("Подходят новичкам:");
        reports.beginnerFriendlyVehicles().forEach(vehicle ->
                System.out.println("  " + vehicle.name() + " [" + vehicle.inventoryNumber() + "]"));

        System.out.println("Весь инвентарь:");
        reports.inventory().forEach(item ->
                System.out.println("  " + item.name() + " [" + item.inventoryNumber() + "]"));

        try {
            registration.registerEquipment(new Helmet("E-001"));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ожидаемый отказ (дубликат): " + exception.getMessage());
        }
    }
}
