package JavaProjectRestaurantManagement;

import java.util.HashMap;
import java.util.List;

public class MenuItem extends GetDataFromFile {
    MenuItem(String fileName) {
        super(fileName);
    }

    public void displayMenu(HashMap<String, List<String>> map, String isAdmin) {
        displayFile(map, isAdmin);
    }

    public void updateMenu(HashMap<String, List<String>> mapFromFileMenu,
            HashMap<String, List<String>> mapFromFileOrder) {
        updateValueByID(mapFromFileMenu, mapFromFileOrder);
    }

    public void displayMenuById(String menuId, HashMap<String, List<String>> map) {
        getvalueFromId(menuId, map);
    }

    public void addMenu(HashMap<String, List<String>> mapFromFileMenu,
            HashMap<String, List<String>> mapFromFileOrder) {
        addNewValue(mapFromFileMenu, mapFromFileOrder);

    }

    public void setMenuStatus(HashMap<String, List<String>> map, String isAdmin) {
        setStatus(map, isAdmin);
    }
}
