package ru.hse.vyshkat;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.hse.vyshkat.model.Bicycle;
import ru.hse.vyshkat.model.ChargingStation;
import ru.hse.vyshkat.model.Helmet;
import ru.hse.vyshkat.model.Scooter;
import ru.hse.vyshkat.service.ConditionBasedServiceCenter;

class DomainValidationTest {
    @Test
    void inspectionUsesConditionNotBeginnerEase() {
        var inspector = new ConditionBasedServiceCenter();
        assertTrue(inspector.inspect(new Bicycle("OK", 1, 6)));
        assertFalse(inspector.inspect(new Bicycle("BAD", 10, 5)));
    }

    @Test
    void invalidInventoryNumbersAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Helmet("  "));
        assertThrows(IllegalArgumentException.class, () -> new Bicycle(null, 6, 7));
    }

    @Test
    void easeAndConditionMustBeInRange() {
        assertThrows(IllegalArgumentException.class, () -> new Bicycle("A", 0, 7));
        assertThrows(IllegalArgumentException.class, () -> new Bicycle("A", 11, 7));
        assertThrows(IllegalArgumentException.class, () -> new Bicycle("A", 7, 0));
        assertThrows(IllegalArgumentException.class, () -> new Bicycle("A", 7, 11));
        assertDoesNotThrow(() -> new Bicycle("B", 1, 10));
    }

    @Test
    void energyMustBeFiniteAndNonnegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Scooter("A", 7, 8, -1));
        assertThrows(IllegalArgumentException.class,
                () -> new Scooter("A", 7, 8, Double.NaN));
        assertThrows(IllegalArgumentException.class,
                () -> new ChargingStation("C", Double.POSITIVE_INFINITY));
        assertDoesNotThrow(() -> new ChargingStation("C", 0));
    }
}
