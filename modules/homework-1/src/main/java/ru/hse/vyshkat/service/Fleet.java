package ru.hse.vyshkat.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import ru.hse.vyshkat.model.InventoryItem;
import ru.hse.vyshkat.model.Vehicle;

/** In-memory inventory shared by the two application services. */
public final class Fleet {
    private final Map<String, InventoryItem> items = new LinkedHashMap<>();

    public void add(InventoryItem item) {
        Objects.requireNonNull(item, "item");
        if (items.putIfAbsent(item.inventoryNumber(), item) != null) {
            throw new IllegalArgumentException(
                    "Duplicate inventory number: " + item.inventoryNumber());
        }
    }

    public List<InventoryItem> allItems() {
        return List.copyOf(items.values());
    }

    public List<Vehicle> vehicles() {
        return items.values().stream()
                .filter(Vehicle.class::isInstance)
                .map(Vehicle.class::cast)
                .toList();
    }
}
