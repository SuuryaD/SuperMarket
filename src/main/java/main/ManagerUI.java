package main;

import sdk.bill.BillDetails;
import sdk.bill.controller.BillControllerImpl;
import sdk.customer.Customer;
import sdk.customer.controller.CustomerController;
import sdk.employee.controller.EmployeeController;
import sdk.employee.domain.Employee;
import sdk.inventory.controller.InventoryController;
import sdk.util.ValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Contains all the functionality of an admin */
public class ManagerUI {

  private final InventoryController inventoryController;
  private final EmployeeController employeeController;
  private final BillControllerImpl billController;
  private final CustomerController customerController;
  private final Employee emp;

  public ManagerUI(Employee emp) {
    this.emp = emp;
    inventoryController = new InventoryController(emp);
    employeeController = new EmployeeController(emp);
    billController = new BillControllerImpl(emp);
    customerController = new CustomerController(emp);
  }

  /** Displays all the options for admin */
  public void menu() {

    String option;
    do {
      System.out.println("Admin Menu: ");
      System.out.println("0. Logout");
      System.out.println("1. View all employee");
      System.out.println("2. Add Employee");
      System.out.println("3. Remove Employee");
      System.out.println("4. View all Products ");
      System.out.println("5. Add New Product");
      System.out.println("6. Restock Existing Products");
      System.out.println("7. View all bills");
      System.out.println("8. View all customers");

      try {
        option = Globals.input.readLine();
        switch (option) {
          case "0":
            return;
          case "1":
            viewAllEmployees();
            break;
          case "2":
            addEmployee();
            break;
          case "3":
            removeEmployee();
            break;
          case "4":
            viewAllProducts();
            break;
          case "5":
            addProduct();
            break;
          case "6":
            restockProduct();
            break;
          case "7":
            viewAllBills();
            break;
          case "8":
            viewAllCustomers();
            break;
          default:
            System.out.println("Enter a valid option");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } while (true);
  }
//TODO
  private void viewAllEmployees() {
    System.out.println(employeeController.printAllEmployees());
  }


  private void addEmployee() throws IOException {

    String name, username, pass, choice;
    do {
      System.out.println("Enter the name of the employee");
      name = Globals.input.readLine();
      if (name.equals("0")) return;
      System.out.println("Enter the username of the employee");
      username = Globals.input.readLine();
      if (username.equals("0")) return;
      System.out.println("Enter the pass of the employee");
      pass = Globals.input.readLine();
      if (pass.equals("0")) return;
      System.out.println("Choose the role of the employee");
      System.out.println("1. Manager");
      System.out.println("2. Biller");
      System.out.println("3. Floor Staff");
      System.out.println("4. Cashier");
      System.out.println("5. Delivery");
      choice = Globals.input.readLine();
      if (choice.equals("0")) return;
      try {
        //TODO try with callback
        if (employeeController.addEmployee(name, username, pass, Integer.parseInt(choice))) {
          System.out.println("Employee added successfully");
          break;
        }
        System.out.println("Failed. Try again(Enter 0 to exit)");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid number");
      }

    } while (true);
  }

  /**
   * Removes an employee from employee list
   *
   * @throws IOException raised when invalid input is provided
   */
  private void removeEmployee() throws IOException {

    String id;

    do {
      System.out.println("Enter the employee Id: ");
      id = Globals.input.readLine();
      if (id.equals("0")) return;

      try {
        if (employeeController.removeEmployee(Integer.parseInt(id))) {
          System.out.println("Employee Removed Successfully");
          break;
        }
        System.out.println("Failed to remove employee. Try again");
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid Number");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }

    } while (true);
  }

  /**
   * Adds a new product to the inventory.
   *
   * @throws IOException raised when invalid input is provided
   */
  private void addProduct() throws IOException {
    String id;
    String name;
    String price;
    String quantity;

    do {

      System.out.println("Enter the product id: (Enter 0 to exit)");
      id = Globals.input.readLine();
      if (id.equals("0")) return;
      System.out.println("Enter the product name:");
      name = Globals.input.readLine();
      if (name.equals("0")) return;
      System.out.println("Enter the product price");
      price = Globals.input.readLine();
      if (price.equals("0")) return;
      System.out.println("Enter the quantity");
      quantity = Globals.input.readLine();
      if (quantity.equals("0")) return;

      try {

        if (inventoryController.addProduct(
            Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(quantity))) {
          System.out.println("Product Added Successfully");
          break;
        } else System.out.println("Adding product failed. Try again.");
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid number");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }
    } while (true);
  }

  /**
   * Restocks the existing product in inventory
   *
   * @throws IOException invalid input
   */
  private void restockProduct() throws IOException {
    String id;
    String quantity;
    do {
      System.out.println("Enter the product id");
      id = Globals.input.readLine();
      if (id.equals("0")) return;
      System.out.println("Enter the quantity to be added");
      quantity = Globals.input.readLine();
      if (quantity.equals("0")) return;
      try {
        if (inventoryController.increaseStock(Integer.parseInt(id), Integer.parseInt(quantity))) {
          System.out.println("Quantity added successfully");
          break;
        } else System.out.println("Adding quantity failed. Try again");
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid number");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }

    } while (true);
  }

  private void viewAllProducts() {
    System.out.println(inventoryController.getAllProducts());
  }

  /**
   * Displays all the bills
   *
   * @throws IOException input error.
   */
  private void viewAllBills() throws IOException {
    viewBills(billController.getAllBills());
  }

  /** Displays all customer details in a tabular form */
  private void viewAllCustomers() throws IOException {

    do {
      System.out.println(printCustomerTable(customerController.getAllCustomers()));
      System.out.println("0. exit");
      System.out.println("1. View Customer bills");
      String option = Globals.input.readLine();
      switch (option) {
        case "0":
          return;
        case "1":
          System.out.println("Enter Customer Id: ");
          String id = Globals.input.readLine();
          if (id.equals("0")) return;
          try {
            viewBills(customerController.getBillsOfCustomer(Integer.parseInt(id)));
          } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Enter a valid serial number");
          }
          break;
        default:
          System.out.println("Enter a valid input");
      }
    } while (true);
  }

  private String printCustomerTable(List<Customer> ls){
    ArrayList<String> headers = new ArrayList<>();
    ArrayList<ArrayList<String>> content = new ArrayList<>();
    headers.add("S.No");
    headers.add("Customer id");
    headers.add("Customer name");
    headers.add("Customer address");
    headers.add("No of purchases");
    headers.add("Total Purchase amount");
    int no = 1;
    for (Customer customer : ls) {
      content.add(
          new ArrayList<>(
              Arrays.asList(
                  String.valueOf(no),
                  String.valueOf(customer.getId()),
                  customer.getName(),
                  customer.getAddress(),
                  String.valueOf(customerController.getTotalPurchaseOfCustomer(customer.getId())),
                  String.valueOf(customerController.getNoOfBillsOfCustomer(customer.getId()))
//                  String.valueOf(billService.getNoOfBillsOfCustomer(customer.getId())),
//                  String.valueOf(billService.getTotalPurchaseOfCustomer(customer.getId()))
              )));
      no++;
    }
    return sdk.util.Globals.printTable(headers, content);
  }
  /**
   * Displays all the bills
   *
   * @throws IOException invalid input
   */
  private void viewBills(List<BillDetails> ls) throws IOException {

    if (ls.isEmpty()) {
      System.out.println("No bills available");
      return;
    }
    int i = 0;
    do {
      System.out.println(ls.get(i));
      System.out.println("0. exit");
      System.out.println("1. Next");
      System.out.println("2. Previous");
      String option = Globals.input.readLine();
      switch (option) {
        case "0":
          return;
        case "1":
          if (i < ls.size() - 1) {
            i++;
          } else {
            System.out.println("No next Bill");
          }
          break;
        case "2":
          if (i > 0) {
            i--;
          } else {
            System.out.println("No previous bill");
          }
          break;
        default:
          System.out.println("Enter a valid option");
      }
    } while (true);
  }
}
