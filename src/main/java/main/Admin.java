package main;

import bill.Bill;
import customer.Customer;
import customer.CustomerList;
import employee.Employee;
import employee.EmployeeList;
import inventory.Inventory;
import inventory.Product;
import util.Globals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Contains all the functionality of an admin */
public class Admin {

  private final Employee currentEmployee;

  public Admin(Employee currentEmployee) {
    this.currentEmployee = currentEmployee;
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
            System.out.println(EmployeeList.getInstance());
            break;
          case "2":
            addEmployee();
            break;
          case "3":
            removeEmployee();
            break;
          case "4":
            System.out.println(Inventory.getInstance());
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

  /**
   * Adds a new employee to the employee list.
   *
   * @throws IOException raised when invalid input is provided
   */
  private void addEmployee() throws IOException {

    String name, username, pass;
    do {
      System.out.println("Enter the Employee name: (press 0 to return back to previous menu)");
      name = Globals.input.readLine();

      if (name.equals("0")) return;

      if (name.matches("^[a-zA-Z]+( [a-zA-Z]+)?$")) break;

      System.out.println("Enter a valid name");

    } while (true);

    do {
      System.out.println("Enter the username for " + name + ": ");
      username = Globals.input.readLine();
      if (username.equals("0")) return;
      if (username.matches("^[a-zA-Z0-9._+-/!@#$%^&*]+$")
          && EmployeeList.getInstance().isUsernameAvailable(username)) break;
      else System.out.println("Username already taken. Enter a different username.");

    } while (true);

    do {
      System.out.println(
          "Enter the password: (must contain atleast one number, special character, Uppercase Letter");
      pass = Globals.input.readLine();

      if (pass.equals("0")) return;
      if (pass.matches("(^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])).{5,20}$")) break;

      System.out.println("Enter a valid password");
    } while (true);

    System.out.println("Is a Admin? (y/n)");
    String choice = Globals.input.readLine();

    EmployeeList.getInstance().addEmployee(name, username, pass, "y".equalsIgnoreCase(choice));
    System.out.println("new employee " + name + "added Successfully");
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

      int idNum;
      try {
        idNum = Integer.parseInt(id);
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid Number");
        continue;
      }

      if (idNum == 0) return;
      if (idNum == currentEmployee.getId()) {
        System.out.println("You cannot delete your own account");
        continue;
      }

      if (EmployeeList.getInstance().removeEmployee(idNum)) {
        System.out.println("Employee Removed Successfully");
        break;
      }

      System.out.println("Invalid employee id. Enter a valid employee id");

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
      System.out.println("Enter the product id: (press 0 to exit)");
      id = Globals.input.readLine();
      if (id.equals("0")) return;
      try {
        if (!Inventory.getInstance().isProductAvailable(Integer.parseInt(id))) break;
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid number");
        continue;
      }

      System.out.println("The product id is already available. Try another id");

    } while (true);

    do {

      System.out.println("Enter the product name: ");
      name = Globals.input.readLine();

      if (name.equals("0")) return;
      if (name.length() > 0) break;
      System.out.println("Enter a valid product name");
    } while (true);

    do {
      System.out.println("Enter the Product price: ");
      price = Globals.input.readLine();

      if (price.equals("0")) return;
      try {
        if (Double.parseDouble(price) > 0) break;
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid price");
        continue;
      }
      System.out.println("Price should be greater than 0");

    } while (true);

    do {
      System.out.println("Enter the product quantity: ");
      quantity = Globals.input.readLine();

      if (quantity.equals("0")) return;
      try {
        if (Integer.parseInt(quantity) > 0) break;
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid quantity");
        continue;
      }
      System.out.println("Quantity must be greater than 0");

    } while (true);

    Inventory.getInstance()
        .add(
            new Product(Integer.parseInt(id), name, Double.parseDouble(price)),
            Integer.parseInt(quantity));
    System.out.println("New Product Added Successfully");
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
      try {
        if (Inventory.getInstance().isProductAvailable(Integer.parseInt(id))) break;
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid number");
        continue;
      }
      System.out.println("No such Product available");
    } while (true);

    do {
      System.out.println("Enter the quantity");
      quantity = Globals.input.readLine();
      if (quantity.equals("0")) return;

      try {
        if (Integer.parseInt(quantity) > 0) break;
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid Number");
        continue;
      }
      System.out.println("Quantity should be greater than 0");
    } while (true);

    Inventory.getInstance().updateStock(Integer.parseInt(id), Integer.parseInt(quantity));
    System.out.println("Product Restock Successful");
  }

  /**
   * Displays all the bills
   *
   * @throws IOException input error.
   */
  private void viewAllBills() throws IOException {
    ArrayList<Bill> bills = new ArrayList<>();

    for (Customer customer : CustomerList.getInstance().getCustomerList()) {
      bills.addAll(customer.getBills());
    }
    if (bills.size() == 0) {
      System.out.println("No bills available");
      return;
    }

    viewBills(bills);
  }

  /** Displays all customer details in a tabular form */
  private void viewAllCustomers() throws IOException {

    ArrayList<String> headers = new ArrayList<>();
    ArrayList<ArrayList<String>> content = new ArrayList<>();
    headers.add("S.No");
    headers.add("Customer id");
    headers.add("Customer name");
    headers.add("No of purchases");
    headers.add("Total Purchase amount");
    int no = 1;
    for (Customer customer : CustomerList.getInstance().getCustomerList()) {

      content.add(
          new ArrayList<>(
              Arrays.asList(
                  String.valueOf(no),
                  String.valueOf(customer.getId()),
                  customer.getName(),
                  String.valueOf(customer.getBills().size()),
                  String.valueOf(customer.getTotalPurchase()))));
      no++;
    }

    do {
      System.out.println(Globals.printTable(headers, content));
      System.out.println("1. View Customer bills");
      System.out.println("0. exit");
      String option = Globals.input.readLine();
      switch (option) {
        case "0":
          return;
        case "1":
          System.out.println("Enter the S.no of Customer");
          String num = Globals.input.readLine();
          try {
            viewBills(
                CustomerList.getInstance()
                    .getCustomerList()
                    .get(Integer.parseInt(num) - 1)
                    .getBills());
          } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Enter a valid serial number");
          }
          break;
        default:
          System.out.println("Enter a valid input");
      }

    } while (true);
  }

  /**
   * Displays all the bills
   *
   * @param bills to be displayed
   * @throws IOException invalid input
   */
  private void viewBills(List<Bill> bills) throws IOException {

    if (bills.isEmpty()) {
      System.out.println("No bills available");
      return;
    }
    int i = 0;
    do {
      System.out.println(bills.get(i));
      System.out.println("0. exit");
      System.out.println("1. Next");
      System.out.println("2. Previous");
      String option = Globals.input.readLine();
      switch (option) {
        case "0":
          return;
        case "1":
          if (i < bills.size() - 1) {
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
