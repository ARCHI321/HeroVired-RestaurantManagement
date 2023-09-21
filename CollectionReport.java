package JavaProjectRestaurantManagement;

import java.util.HashMap;
import java.util.List;

public abstract class CollectionReport {
    public static void findReport() {
        Order order = new Order("Order");
        HashMap<String, List<String>> orderMap;
        orderMap = order.HashMapFromTextFile();
        int totalNumberOfOrders = orderMap.size();
        int totalNumberOfOrderDelivered = 0, totalNumberOfOrderCancelled = 0, totalNumberOfOrderPending = 0;
        for (String s : orderMap.keySet()) {
            if (orderMap.get(s).get(3).equals("Delivered")) {
                totalNumberOfOrderDelivered++;
            } else if (orderMap.get(s).get(3).equals("Cancelled")) {
                totalNumberOfOrderCancelled++;
            } else if (orderMap.get(s).get(3).equals("Ordered")) {
                totalNumberOfOrderPending++;
            }
        }

        System.out.println("Total Number of Orders : " + totalNumberOfOrders);
        System.out.println("Total Number of Orders Delivered Succesfully: " + totalNumberOfOrderDelivered);
        System.out.println("Total Number of Orders Cancelled: " + totalNumberOfOrderCancelled);
        System.out.println("Total Number of Orders Yet to Delivered: " + totalNumberOfOrderPending);
        System.out.println();
        System.out.println(
                "Percentage of Successful Orders : "
                        + ((float) totalNumberOfOrderDelivered / (float) totalNumberOfOrders) * 100 + "%");
        System.out.println(
                "Percentage of Cancelled Orders : "
                        + ((float) totalNumberOfOrderCancelled / (float) totalNumberOfOrders) * 100 + "%");
        System.out.println(
                "Percentage of Pending Orders : "
                        + ((float) totalNumberOfOrderPending / (float) totalNumberOfOrders) * 100 + "%");
    }
}
