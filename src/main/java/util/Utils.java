package util;

import customer.Customer;
import customer.CustomerList;
import inventory.InventoryService;
import inventory.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/** Utility class to initialize the project */
public class Utils {

//  public static void initializeTempInventory() {
//    InventoryService.getInstance().add(new Product(1, "Toothpaste", 10.23), 25);
//    InventoryService.getInstance().add(new Product(2, "Sugar", 14.23), 15);
//    InventoryService.getInstance().add(new Product(3, "Cereal", 20.00), 150);
//    InventoryService.getInstance().add(new Product(4, "Shampoo", 9.23), 59);
//    InventoryService.getInstance().add(new Product(5, "Soap", 18.231), 5);
//  }

//  public static void initializeTempEmployeeList() {
//
//    EmployeeList.getInstance().addEmployee("Surya", "admin1", "admin1", true);
//    EmployeeList.getInstance().addEmployee("Dhanush", "user2", "user2", false);
//    EmployeeList.getInstance().addEmployee("user3", "user3", "user3", false);
//    EmployeeList.getInstance().addEmployee("user4", "user4", "user4", false);
//    EmployeeList.getInstance().addEmployee("user5", "user5", "user5", false);
//  }

  public static void initializeTempCustomerList() {
    CustomerList.getInstance().addCustomer(new Customer("Surya", "k.k.nagar"));
    CustomerList.getInstance().addCustomer(new Customer("Dhanush", "Chennai"));
  }

  public static void initializeEmployeeTable(){
    Connection con = ConnectDatabase.getConnection();
    try {
      String url = "CREATE TABLE IF NOT EXISTS employees " +
              "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
              "name TEXT NOT NULL," +
              "username TEXT NOT NULL, " +
              "password TEXT NOT NULL, " +
              "admin INTEGER NOT NULL)";


      Statement stmt = con.createStatement();
      stmt.execute(url);
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  public static void initializeInventory(){

    Connection con = ConnectDatabase.getConnection();
    try {
      String url = "CREATE TABLE IF NOT EXISTS inventory "+
              "(id INTEGER PRIMARY KEY, " +
              "name TEXT NOT NULL, " +
              "price REAL NOT NULL, " +
              "quantity INTEGER NOT NULL)";


      Statement stmt = con.createStatement();
      stmt.execute(url);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void initializeBill(){
    Connection con = ConnectDatabase.getConnection();
    try {
      String url = "CREATE TABLE IF NOT EXISTS bills "+
              "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
              "time INTEGER NOT NULL, " +
              "employee_id INTEGER NOT NULL, " +
              "employee_name TEXT NOT NULL, " +
              "amount REAL NOT NULL)";

      Statement stmt = con.createStatement();
      stmt.execute(url);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void initializeBillItems(){
    Connection con = ConnectDatabase.getConnection();
    try {
      String url = "CREATE TABLE IF NOT EXISTS bill_items "+
              "(bill_id INTEGER NOT NULL, " +
              "id INTEGER NOT NULL, " +
              "quantity INTEGER NOT NULL, " +
              "PRIMARY KEY (bill_id, id))";


      Statement stmt = con.createStatement();
      stmt.execute(url);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
