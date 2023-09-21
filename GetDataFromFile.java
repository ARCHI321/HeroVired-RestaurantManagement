package JavaProjectRestaurantManagement;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public abstract class GetDataFromFile {
    String fileName = "";
    String filePath = "";
    ArrayList<String> colList = new ArrayList<>();

    GetDataFromFile(String fileName) {
        this.fileName = fileName;
        filePath = "E:\\Hero Vired\\JavaProjectRestaurantManagement\\" + fileName + ".csv";
        if (fileName.equals("Menu")) {
            colList.add("MenuId");
            colList.add("Category");
            colList.add("Name");
            colList.add("Price");
            colList.add("Status");

        } else if (fileName.equals("Order")) {
            colList.add("OrderId");
            colList.add("MenuItems&Quantity");
            colList.add("Total");
            colList.add("Date");
            colList.add("Status");

        }

    }

    static int totalColsCount;

    // public static void main(String[] args) {

    // MenuItem m = new MenuItem("Menu");
    // HashMap<String, List<String>> mMap = m.HashMapFromTextFile();
    // Order o = new Order("Order");
    // HashMap<String, List<String>> oMap = o.HashMapFromTextFile();
    // // System.out.println(mMap);
    // // System.out.println(oMap);
    // // m.displayMenu(mMap, "Yes");
    // // // m.updateMenu(mMap, oMap);
    // // // m.addMenu(mMap, oMap);
    // // m.setMenuStatus(mMap);

    // // o.displayOrder(oMap);
    // // o.updateOrder(mMap, oMap);
    // o.addOrder(mMap, oMap);
    // // o.setOrderStatus(oMap);
    // }

    public void displayFile(HashMap<String, List<String>> map, String isAdmin) {
        // HashMap<String, List<String>> map = HashMapFromTextFile();
        for (String s : colList) {

            System.out.print(s + " ");
        }
        System.out.println();
        for (String s : map.keySet()) {
            if (!map.get(s).contains("Not Available") && isAdmin.equals("No")) {
                System.out.println(s + ":" + map.get(s));
            } else {
                System.out.println(s + ":" + map.get(s));
            }
        }
    }

    public HashMap<String, List<String>> HashMapFromTextFile() {

        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        BufferedReader br = null;
        int count = 0;
        try {

            File file = new File(filePath);

            br = new BufferedReader(new FileReader(file));

            String line = null;
            while ((line = br.readLine()) != null) {
                ArrayList<String> values = new ArrayList<>();
                String[] parts = line.split(",");

                if (count == 0) {
                    count++;
                    continue;
                }
                String menuId = parts[0].trim();
                for (int i = 1; i < parts.length; i++) {
                    values.add(parts[i]);
                }

                if (!menuId.equals(""))
                    map.put(menuId, values);
                totalColsCount = values.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
                ;
            }
        }

        return map;
    }

    public List<String> getvalueFromId(String id, HashMap<String, List<String>> mapFromFile) {
        // HashMap<String, List<String>> mapFromFile = HashMapFromTextFile();
        if (mapFromFile.keySet().contains(id)) {
            return mapFromFile.get(id);
        } else {
            System.out.println("Wrong Menu Id");
            return null;
        }
    }

    public Map.Entry<String, List<String>> getLastId(HashMap<String, List<String>> mapFromFile) {
        Map.Entry<String, List<String>> lastEntry = null;
        // HashMap<String, List<String>> mapFromFile = HashMapFromTextFile();
        lastEntry = mapFromFile.entrySet().stream().reduce((first, second) -> second).get();
        return lastEntry;
    }

    public void setStatus(HashMap<String, List<String>> mapFromFile, String isAdmin) {
        Scanner sc = new Scanner(System.in);
        // HashMap<String, List<String>> mapFromFile = HashMapFromTextFile();
        if (fileName.equals("Menu")) {
            System.out.print("Enter the menu id you want to update : ");
            String menuId = sc.nextLine();
            if (mapFromFile.keySet().contains(menuId)) {
                System.out.println("The Menu is as follows : ");
                List<String> values = getvalueFromId(menuId, mapFromFile);
                System.out.println(values);
                if (values.get(3).equals("Available")) {
                    values.set(3, "Not Available");
                } else {
                    values.set(3, "Available");
                }
                mapFromFile.put(menuId, values);
                HashMapToTextFile(mapFromFile);
            }
        }

        if (fileName.equals("Order")) {
            System.out.print("Enter the order id you want to update : ");
            String orderId = sc.nextLine();
            if (mapFromFile.keySet().contains(orderId)) {
                System.out.println("The Menu is as follows : ");
                List<String> values = getvalueFromId(orderId, mapFromFile);
                System.out.println(values);
                if (isAdmin.equals("Yes")) {
                    System.out.print("Update the Status(1.Order / 2.Delivered / 3.Cancelled) [Enter the id]");
                    int newStatus = sc.nextInt();
                    switch (newStatus) {
                        case 1:
                            values.set(3, "Ordered");
                            break;
                        case 2:
                            values.set(3, "Delivered");
                            break;
                        case 3:
                            values.set(3, "Cancelled");
                            break;
                    }
                } else {
                    System.out.print("Do you want cancel the order(y/n) : ");
                    String confirm = sc.nextLine();
                    if (confirm.equals("y")) {
                        System.out.println("Your order has been cancelled");
                        values.set(3, "Cancelled");
                    }
                }

                mapFromFile.put(orderId, values);
                HashMapToTextFile(mapFromFile);
            }
        }
    }

    public void addNewValue(HashMap<String, List<String>> mapFromFileMenu,
            HashMap<String, List<String>> mapFromFileOrder) {
        Scanner sc = new Scanner(System.in);
        double total = 0.0;
        Map.Entry<String, List<String>> lastEntry = null;
        // HashMap<String, List<String>> mapFromFile = HashMapFromTextFile();
        if (fileName.equals("Order")) {
            lastEntry = getLastId(mapFromFileOrder);
        } else if (fileName.equals("Menu")) {
            lastEntry = getLastId(mapFromFileMenu);
        }

        ArrayList<String> allValues = new ArrayList<>();

        if (fileName.equals("Order")) {
            String newOrderId = Integer.toString(Integer.parseInt(lastEntry.getKey()) + 1);
            String conf = "", conf1 = "", menu = "", allMenus = "";
            int quantity = 0;
            for (int i = 1; i < colList.size(); i++) {
                if (i == 1) {
                    do {
                        System.out.print("Do you want to look at the menu (y/n) ? : ");
                        conf1 = sc.nextLine();
                        if (conf1.equals("y")) {
                            displayFile(mapFromFileMenu, "No");
                        }
                        System.out.print("Enter menuId : ");
                        int menuId = sc.nextInt();
                        menu = mapFromFileMenu.get(Integer.toString(menuId)).get(1);
                        System.out.println("Menu : " + menu);
                        System.out.print("Enter quantity : ");
                        quantity = sc.nextInt();
                        allMenus += menu + "x" + Integer.toString(quantity) + " ";

                        for (String s : mapFromFileMenu.keySet()) {
                            if (mapFromFileMenu.get(s).contains(menu)) {
                                String p = mapFromFileMenu.get(s).get(2);
                                int price = Integer.parseInt(p);
                                total += price * quantity;
                            }
                        }
                        sc.nextLine();
                        System.out.print("Do you want to add new item(yes/no>) ? : ");
                        conf = sc.nextLine();
                    } while (conf.equals("yes"));

                    allValues.add(allMenus);

                    continue;
                }
                if (i == 2) {
                    System.out.print("Enter " + colList.get(i) + ": ");
                    System.out.print(total);
                    allValues.add(Double.toString(total));
                    continue;
                }
                if (i == 3) {
                    String current_date = java.time.LocalDate.now().toString();
                    String currentDay = current_date.substring(current_date.lastIndexOf("-") + 1,
                            current_date.length());
                    String currentMonth = current_date.substring(current_date.lastIndexOf("-") - 2,
                            current_date.lastIndexOf("-"));
                    String currentYear = current_date.substring(0, current_date.indexOf("-"));

                    String orderDate = currentDay + "-" + currentMonth + "-" + currentYear;
                    allValues.add(orderDate);
                    System.out.println();
                    System.out.print("Enter " + colList.get(i) + ": ");
                    System.out.println(orderDate);
                    continue;
                }
                if (i == 4) {
                    System.out.print("Enter " + colList.get(i) + ": ");
                    System.out.print("Ordered");
                    allValues.add("Ordered");
                    System.out.println();
                    System.out.println();
                    System.out.println("Your order id : " + newOrderId);
                    continue;
                }
            }

            mapFromFileOrder.put(newOrderId, allValues);
            HashMapToTextFile(mapFromFileOrder);

        } else if (fileName.equals("Menu")) {
            String newMenuId = Integer.toString(Integer.parseInt(lastEntry.getKey()) + 1);
            for (int i = 1; i < colList.size(); i++) {
                if (i == colList.size() - 1) {
                    System.out.print("Enter " + colList.get(i) + "(Available/Not Available)" + ": ");
                    allValues.add(sc.nextLine());
                    continue;
                }
                System.out.print("Enter " + colList.get(i) + ": ");
                allValues.add(sc.nextLine());
            }
            mapFromFileMenu.put(newMenuId, allValues);
            HashMapToTextFile(mapFromFileMenu);
        }
    }

    public void updateValueByID(HashMap<String, List<String>> mapFromFileMenu,
            HashMap<String, List<String>> mapFromFileOrder) {
        Scanner sc = new Scanner(System.in);
        // HashMap<String, List<String>> mapFromFile = HashMapFromTextFile();
        if (fileName.equals("Menu")) {
            System.out.print("Enter the menu id you want to update : ");
            String menuId = sc.nextLine();
            if (mapFromFileMenu.keySet().contains(menuId)) {
                System.out.println("The order is as follows : ");
                List<String> values = getvalueFromId(menuId, mapFromFileMenu);
                System.out.println(values);
                System.out.print("Do you want update(y/n) ? ");
                String ans = sc.nextLine();
                if (ans.equals("y")) {
                    for (int i = 1; i < colList.size() - 1; i++) {
                        System.out.println((i) + "." + colList.get(i));
                    }
                    System.out.print("What do you want to update (Enter id from above) : ");
                    int colname = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter the updated value : ");
                    String updateValue = sc.nextLine();
                    values.set(colname - 1, updateValue);
                    mapFromFileMenu.put(menuId, values);
                    HashMapToTextFile(mapFromFileMenu);

                }

            } else {
                System.out.println("Wrong menuid entered");
            }
        } else if (fileName.equals("Order")) {
            System.out.print("Enter the order id you want to update : ");
            String orderId = sc.nextLine();
            if (mapFromFileOrder.keySet().contains(orderId)) {
                System.out.println("The order is as follows : ");
                List<String> values = getvalueFromId(orderId, mapFromFileOrder);
                System.out.println(values);
                System.out.print("Do you want update(y/n) ? ");
                String ans = sc.nextLine();
                if (ans.equals("y")) {
                    for (int i = 1; i < colList.size() - 1; i++) {
                        System.out.println((i) + "." + colList.get(i));
                    }
                    System.out.print("What do you want to update (Enter id from above) : ");
                    int colname = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter the updated value : ");
                    String updateValue = sc.nextLine();
                    values.set(colname - 1, updateValue);
                    mapFromFileOrder.put(orderId, values);
                    HashMapToTextFile(mapFromFileOrder);

                }

            } else {
                System.out.println("Wrong menuid entered");
            }
        }

    }

    public void HashMapToTextFile(Map<String, List<String>> map) {

        File file = new File(filePath);

        BufferedWriter bf = null;
        int count = 0;

        try {

            bf = new BufferedWriter(new FileWriter(file));
            for (String a : colList) {
                bf.append(a)
                        .append(',');
            }
            bf.newLine();

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                bf.append(entry.getKey())
                        .append(',');
                for (int i = 0; i < entry.getValue().size(); i++) {
                    bf.append(entry.getValue().get(i))
                            .append(',');
                }

                bf.newLine();
            }

            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {

                bf.close();
            } catch (Exception e) {
            }
        }
    }

}
