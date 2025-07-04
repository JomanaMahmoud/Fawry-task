package model;

import Interfaces.Shippable; // Assuming this is your package for interfaces
import java.util.List;
import java.util.Map;

public class ShippingService {

    public void processShipment(Map<Shippable, Integer> shippableItems) {
        // Do nothing if there are no items to ship.
        if (shippableItems.isEmpty()) {
            return;
        }

        System.out.println("\n** Shipment Notice **");
        double totalWeight = 0; // Initialize total weight in grams.

        // Loop through each shippable item and its quantity.
        for (Map.Entry<Shippable, Integer> entry : shippableItems.entrySet()) {
            Shippable item = entry.getKey();
            int quantity = entry.getValue();

            // Calculate the total weight for this specific item type.
            double itemTotalWeight = item.getWeight() * quantity;
            totalWeight += itemTotalWeight; // Add to the package's total weight.

            // Print the details for this item.
            System.out.println(quantity + "x " + item.getName() + "\t" + itemTotalWeight + "g");
        }

        // Convert and print the final total weight in kilograms.
        System.out.println("Total package weight: " + (totalWeight / 1000) + "kg");
    }
}