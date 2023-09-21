package JavaProjectRestaurantManagement;

import java.util.HashMap;
import java.util.List;

public class Order extends GetDataFromFile {
    Order(String fileName) {
        super(fileName);
    }

    public void displayOrder(HashMap<String, List<String>> map) {
        displayFile(map, "No");
    }

    public void displayOrderById(String orderId, HashMap<String, List<String>> map) {
        System.out.println(getvalueFromId(orderId, map));
    }

    public void updateOrder(HashMap<String, List<String>> mapFromFileMenu,
            HashMap<String, List<String>> mapFromFileOrder) {
        updateValueByID(mapFromFileMenu, mapFromFileOrder);
    }

    public void addOrder(HashMap<String, List<String>> mapFromFileMenu,
            HashMap<String, List<String>> mapFromFileOrder) {
        addNewValue(mapFromFileMenu, mapFromFileOrder);
    }

    public void setOrderStatus(HashMap<String, List<String>> map, String isAdmin) {
        setStatus(map, isAdmin);
    }

}
