package JavaProjectRestaurantManagement;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import BasicsOFJava.PracticePrograms.Arrays.sortArray;

public class GetDataFromOrder {
    final static String filePath = "E:\\Hero Vired\\JavaProjectRestaurantManagement\\Menu.csv";

    public static void main(String[] args) {

        HashMap<String, List<String>> mapFromFile = HashMapFromTextFile(0);

        for (Entry<String, List<String>> entry : mapFromFile.entrySet()) {
            System.out.println(entry.getKey() + " : "
                    + entry.getValue());
        }
        addNewValue();

    }

    public static HashMap<String, List<String>> HashMapFromTextFile(int flag) {

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

                if (flag == 1) {
                    if (count == 0) {
                        for (int i = 0; i < parts.length; i++) {
                            System.out.print(parts[i] + " ");
                        }
                    }
                    count++;
                    System.out.println();
                    continue;
                }
                String menuId = parts[0].trim();
                values.add(parts[1]);
                values.add(parts[2]);
                values.add(parts[3]);
                values.add(parts[4]);

                if (!menuId.equals(""))
                    map.put(menuId, values);
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

    public static List<String> getvalueFromId(String id) {
        HashMap<String, List<String>> mapFromFile = HashMapFromTextFile(0);
        if (mapFromFile.keySet().contains(id)) {
            return mapFromFile.get(id);
        } else {
            System.out.println("Wrong Menu Id");
            return null;
        }
    }

    public static Map.Entry<String, List<String>> getLastId() {
        Map.Entry<String, List<String>> lastEntry = null;
        HashMap<String, List<String>> mapFromFile = HashMapFromTextFile(0);
        lastEntry = mapFromFile.entrySet().stream().reduce((first, second) -> second).get();
        return lastEntry;
    }

    public static void addNewValue() {

        Scanner sc = new Scanner(System.in);
        HashMap<String, List<String>> mapFromFile = HashMapFromTextFile(0);
        Map.Entry<String, List<String>> lastEntry = getLastId();
        String newMenuId = Integer.toString(Integer.parseInt(lastEntry.getKey()) + 1);
        System.out.print("Enter Category : ");
        String category = sc.nextLine();
        System.out.print("Enter Menu : ");
        String menu = sc.nextLine();
        System.out.print("Enter Price : ");
        String price = sc.nextLine();
        System.out.print("Enter Status : ");
        String status = sc.nextLine();
        ArrayList<String> allValues = new ArrayList<>(Arrays.asList(category, menu, price, status));
        mapFromFile.put(newMenuId, allValues);
        HashMapToTextFile(mapFromFile);
    }

    public static void updateValueByID() {
        Scanner sc = new Scanner(System.in);
        HashMap<String, List<String>> mapFromFile = HashMapFromTextFile(0);
        System.out.print("Enter the menu id you want to update : ");
        String menuId = sc.nextLine();
        if (mapFromFile.keySet().contains(menuId)) {
            System.out.println("The order is as follows : ");
            List<String> values = getvalueFromId(menuId);
            System.out.println(values);
            System.out.print("Do you want update(y/n) ? ");
            String ans = sc.nextLine();
            List<String> cols = new ArrayList<>(Arrays.asList("Category", "Name", "Price", "Status"));
            if (ans.equals("y")) {
                System.out.println("1. Category 2.Menu 3.Price 4. Status");
                System.out.print("What do you want to update (Enter id from above) : ");
                int colname = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter the updated value : ");
                String updateValue = sc.nextLine();
                values.set(colname - 1, updateValue);
                mapFromFile.put(menuId, values);
                HashMapToTextFile(mapFromFile);

            }

        } else {
            System.out.println("Wrong menuid entered");
        }

    }

    public static void HashMapToTextFile(Map<String, List<String>> map) {

        File file = new File(filePath);

        BufferedWriter bf = null;
        int count = 0;

        try {

            bf = new BufferedWriter(new FileWriter(file));
            List<String> cols = new ArrayList<>(Arrays.asList("MenuID", "Category", "Name", "Price", "Status"));
            for (String a : cols) {
                bf.append(a)
                        .append(',');
            }
            System.out.println();

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                if (count == 0) {
                    count++;
                    continue;

                }
                bf.append(entry.getKey())
                        .append(',')
                        .append(entry.getValue().get(0))
                        .append(',')
                        .append(entry.getValue().get(1))
                        .append(',')
                        .append(entry.getValue().get(2))
                        .append(',')
                        .append(entry.getValue().get(3));

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
