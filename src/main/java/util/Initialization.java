package util;

import employee.EmployeeList;
import inventory.Inventory;
import inventory.Product;

/**
 * Utility class to initialize the project
 */

public class Initialization {

    public static void initialiseInventory(){

        Inventory.getInstance().add(new Product(1, "toothpaste", 10.23), 25);
        Inventory.getInstance().add(new Product(2, "Sugar", 14.23), 15);
        Inventory.getInstance().add(new Product(3, "Cereals", 20.00), 150);
        Inventory.getInstance().add(new Product(4, "Shampoo", 9.23), 59);
        Inventory.getInstance().add(new Product(5, "Soap", 18.231), 5);
    }

    public static void initialiseEmployee(){

        EmployeeList.addEmployee("Surya", "admin1", "admin1", true);
        EmployeeList.addEmployee("Dhanush", "user2", "user2", false);
        EmployeeList.addEmployee("user3", "user3", "user3", false);
        EmployeeList.addEmployee("user4", "user4", "user4", false);
        EmployeeList.addEmployee("user5", "user5", "user5", false);

    }

}
