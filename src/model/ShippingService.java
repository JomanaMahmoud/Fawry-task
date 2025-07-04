package model;

import Interfaces.Shippable;
import java.util.List;
import java.util.Map;

public class ShippingService {


    public void processShipment(Map<Shippable, Integer> shippableItems) {
        if (shippableItems.isEmpty()) {
            return;
        }

        System.out.println("\n** Shipment Notice **");
        double totalWeight = 0;

        for (Map.Entry<Shippable, Integer> entry : shippableItems.entrySet()) {
            Shippable item = entry.getKey();
            int quantity = entry.getValue();
            double itemTotalWeight = item.getWeight() * quantity;
            totalWeight += itemTotalWeight;
            System.out.println(quantity + "x " + item.getName() + "\t" + itemTotalWeight + "g");
        }

        // Convert total weight to kg for the final summary
        System.out.println("Total package weight: " + (totalWeight / 1000) + "kg");
    }
}