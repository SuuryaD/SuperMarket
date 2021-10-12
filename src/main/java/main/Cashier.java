package main;

import bill.Bill;
import customer.Customer;
import customer.CustomerList;
import employee.Employee;
import inventory.Inventory;
import inventory.InventoryItem;
import util.Globals;

import java.io.IOException;

/** Contains all the functionality of the user employee. */
public class Cashier {

  private final Employee employee;

  public Cashier(Employee employee) {

    this.employee = employee;
  }

  /** Displays the menu and redirects accordingly */
  public void menu() {

    String choice;
    do {
      System.out.println("Menu:");
      System.out.println("1. Billing");
      System.out.println("2. View all Products");
      System.out.println("0. Logout");
      System.out.println("Enter an option");
      try {
        choice = Globals.input.readLine();
        switch (choice) {
          case "0":
            return;
          case "1":
            newBill();
            break;
          case "2":
            System.out.println(Inventory.getInstance());
          default:
            System.out.println("Enter a valid option");
            break;
        }
      } catch (IOException e) {
        System.out.println("Internal error");
        System.exit(1);
      }
    } while (true);
  }

  private void newBill() throws IOException {

    System.out.println("1. New Customer");
    System.out.println("2. Existing Customer");
    String option = Globals.input.readLine();

    switch (option) {
      case "0":
        return;
      case "1":
        newCustomer();
        return;
      case "2":
        System.out.println("Enter the customer id: ");
        String id = Globals.input.readLine();
        Customer customer;
        try {
          customer = CustomerList.getInstance().getCustomerById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
          System.out.println("Enter a valid id.");
          break;
        }

        if (customer != null) {
          generateBill(customer);
          return;
        }
        System.out.println("Customer id invalid");
        break;
      default:
        System.out.println("Enter a valid input");
    }
  }

  private void newCustomer() throws IOException {

    System.out.println("Enter the details for new customer");
    String name;
    String address;

    do {
      System.out.println("Enter Customer Name: ");
      name = Globals.input.readLine();
      if (name.equals("0")) return;
      if (name.matches("^[a-zA-Z]+( [a-zA-Z]+)?$")) break;
      else System.out.println("enter a valid name");
    } while (true);

    do {
      System.out.println("Enter the Customer Address: ");
      address = Globals.input.readLine();
      if (address.equals("0")) return;
      if (address.length() != 0) break;
      System.out.println("Enter a valid address");

    } while (true);

    Customer customer = new Customer(name, address);
    CustomerList.getInstance().addCustomer(customer);
    generateBill(customer);
  }

  /**
   * Generates a new bill
   *
   * @param customer customer to which billing is done
   * @throws IOException raised when invalid input
   */
  private void generateBill(Customer customer) throws IOException {

    Bill bill = new Bill(employee.getId(), employee.getName());

    do {
      System.out.println("Menu: ");
      System.out.println("1. Add Product");
      System.out.println("2. Cancel Bill");
      if (!bill.getBillItems().isEmpty()) {
        System.out.println("3. Remove Product");
        System.out.println("4. Payment");
      }
      String option = Globals.input.readLine();
      switch (option) {
        case "1":
          addProduct(bill);
          System.out.println(bill);
          break;
        case "2":
          System.out.println("Do you want to cancel bill?(y/n)");
          String in = Globals.input.readLine();
          if (in.equalsIgnoreCase("y")) {
            bill.cancelBill();
            return;
          }
          break;
        case "3":
          if (bill.getBillItems().isEmpty()) {
            System.out.println("Enter a valid input");
            break;
          }
          removeProduct(bill);
          System.out.println(bill);
          break;
        case "4":
          if (bill.getBillItems().isEmpty()) {
            System.out.println("Enter a valid input");
            break;
          }

          if (new Payment().menu()) {
            System.out.println("Payment Successful");
            customer.addBill(bill);
            return;
          }
          System.out.println("Payment Failed");
          break;
        default:
          System.out.println("Enter a valid option");
      }

    } while (true);
  }

  /**
   * Adds product to the bill
   *
   * @param bill bill to which product is to be added
   * @throws IOException input error
   * @throws NumberFormatException invalid input
   */
  private void addProduct(Bill bill) throws IOException {

    InventoryItem item;
    String id;
    do {
      System.out.println("Enter Product id: (Enter 0 to exit)");
      id = Globals.input.readLine();
      if (id.equals("0")) return;
      try {
        item = Inventory.getInstance().getProductById(Integer.parseInt(id));
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid id.");
        continue;
      }
      if (item != null) break;

      System.out.println("Product does not exist. Enter a valid Product ID");
    } while (true);

    do {
      System.out.println("Enter the quantity: (Enter 0 to exit) ");
      String quantity = Globals.input.readLine();

      if (quantity.equals("0")) return;
      int quantityInt;

      try {
        quantityInt = Integer.parseInt(quantity);
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid quantity");
        continue;
      }

      if (quantityInt < 0) {
        System.out.println("Enter a valid quantity");
      } else if (item.getQuantity() < quantityInt) {
        System.out.println(
            "The requested quantity is not available. Available Quantity is " + item.getQuantity());
      } else {
        bill.addItem(item, quantityInt);
        break;
      }
    } while (true);
  }

  /**
   * Removes product from the bill
   *
   * @param bill bill
   * @throws IOException input error
   * @throws NumberFormatException invalid input
   */
  private void removeProduct(Bill bill) throws IOException, NumberFormatException {

    String number;
    String quantity;
    do {
      System.out.println("Enter the item No: ");
      number = Globals.input.readLine();
      if (number.equals("0")) return;
      try {
        if (bill.getBillItems().size() < Integer.parseInt(number) || Integer.parseInt(number) < 0) {
          System.out.println("Enter a valid item Number.");
        } else break;
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid number");
      }
    } while (true);

    do {
      System.out.println("Enter the quantity to be removed");
      quantity = Globals.input.readLine();

      if (quantity.equals("0")) return;

      int qnt = bill.getBillItems().get(Integer.parseInt(number) - 1).getQuantity();
      int removeQuantity;
      try {
        removeQuantity = Integer.parseInt(quantity);
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid number");
        continue;
      }
      if (qnt < removeQuantity || removeQuantity < 0)
        System.out.println("Invalid Quantity. Enter a valid quantity");
      else {
        bill.removeProduct(Integer.parseInt(number), Integer.parseInt(quantity));
        break;
      }

    } while (true);
  }
}
