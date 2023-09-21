package JavaProjectRestaurantManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class RestaurantApp extends CollectionReport {

    static String adminPassword;
    static MenuItem menu;
    static Order order;

    public static void main(String[] args) {
        String ans = "";
        adminPassword = "admin123";
        menu = new MenuItem("Menu");
        HashMap<String, List<String>> menuMap;
        menuMap = menu.HashMapFromTextFile();
        order = new Order("Order");
        HashMap<String, List<String>> orderMap;
        orderMap = order.HashMapFromTextFile();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Quick Bites App");
        System.out.print("Who are you ? 1.Admin  2.Customer (Enter the number) : ");
        int n = sc.nextInt();
        sc.nextLine();
        if (n == 1) {
            System.out.print("Enter admin password : ");
            String enterPassword = sc.nextLine();
            if (enterPassword.equals(adminPassword)) {
                System.out.println("Welcome Admin");
                do {

                    System.out.println(
                            "1. View Menu   2. View order   3. Update Order   4. Update Menu   5.Add a new menu   6.Set Order status   7.Set Menu Status  8.Report Collection  9.Exit");
                    System.out.print("Enter your choice : ");
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            menu.displayMenu(menuMap, "Yes");
                            break;
                        case 2:
                            order.displayOrder(orderMap);
                            break;
                        case 3:
                            order.updateOrder(menuMap, orderMap);
                            break;
                        case 4:
                            menu.updateMenu(menuMap, orderMap);
                            break;
                        case 5:
                            menu.addMenu(menuMap, orderMap);
                            break;
                        case 6:
                            order.setOrderStatus(orderMap, "Yes");
                            break;
                        case 7:
                            menu.setMenuStatus(orderMap, "Yes");
                            break;
                        case 8:
                            findReport();
                            break;
                        case 9:
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Wrong value entered");

                    }

                    sc.nextLine();
                    System.out.print("Do you want to continue(yes/no) : ");
                    ans = sc.nextLine();
                } while (ans.equals("yes"));

            } else {
                System.out.println("Entered Wrong password");
            }
        } else if (n == 2) {

            System.out.print("Enter you name : ");
            String customerName = sc.nextLine();
            System.out.println("Welcome " + customerName);

            do {
                System.out.println(
                        "1. View Menu   2. Place an order   3. Cancel an Order   4.Display my Order  5.Exit");
                System.out.print("Enter your choice : ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        menu.displayMenu(menuMap, "No");
                        break;
                    case 2:
                        order.addOrder(menuMap, orderMap);
                        break;
                    case 3:
                        order.setOrderStatus(orderMap, "No");
                        break;
                    case 4:
                        sc.nextLine();
                        System.out.print("Enter your order id : ");
                        String orderId = sc.nextLine();
                        order.displayOrderById(orderId, orderMap);
                        break;
                    case 5:
                        System.out.println("Thanks for visiting our store!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Wrong value entered");

                }
                sc.nextLine();
                System.out.print("Do you want to continue(yes/no) : ");
                ans = sc.nextLine();
            } while (ans.equals("yes"));
        }
    }
}
