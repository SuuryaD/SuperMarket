package util;

import customer.Customer;
import customer.CustomerList;
import employee.EmployeeList;
import inventory.Inventory;
import inventory.Product;

/** Utility class to initialize the project */
public class Utils {

  public static void initializeTempInventory() {
    Inventory.getInstance().add(new Product(1, "Toothpaste", 10.23), 25);
    Inventory.getInstance().add(new Product(2, "Sugar", 14.23), 15);
    Inventory.getInstance().add(new Product(3, "Cereal", 20.00), 150);
    Inventory.getInstance().add(new Product(4, "Shampoo", 9.23), 59);
    Inventory.getInstance().add(new Product(5, "Soap", 18.231), 5);
  }

  public static void initializeTempEmployeeList() {

    EmployeeList.getInstance().addEmployee("Surya", "admin1", "admin1", true);
    EmployeeList.getInstance().addEmployee("Dhanush", "user2", "user2", false);
    EmployeeList.getInstance().addEmployee("user3", "user3", "user3", false);
    EmployeeList.getInstance().addEmployee("user4", "user4", "user4", false);
    EmployeeList.getInstance().addEmployee("user5", "user5", "user5", false);
  }

  public static void initializeTempCustomerList() {
    CustomerList.getInstance().addCustomer(new Customer("Surya", "k.k.nagar"));
    CustomerList.getInstance().addCustomer(new Customer("Dhanush", "Chennai"));
  }
}
