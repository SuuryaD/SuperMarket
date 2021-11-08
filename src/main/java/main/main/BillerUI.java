package main.main;

import main.callback.CallBackImpl;
import main.callback.ViewAllProducts;
import main.utils.Globals;
import sdk.bill.BillDetails;
import sdk.bill.controller.BillController;
import sdk.customer.controller.CustomerController;
import sdk.employee.domain.Employee;
import sdk.inventory.controller.InventoryController;
import sdk.util.Factory;
import sdk.util.ValidationException;

import java.io.IOException;

/** UI for the Billing Employee */
public class BillerUI {

  private final InventoryController inventoryControllerImpl;
  private final BillController billController;
  private final CustomerController customerController;
  private final Employee emp;

  public BillerUI(Employee emp) {
    this.emp = emp;
    this.inventoryControllerImpl = Factory.createInventoryController(emp);
    this.billController = Factory.createBillController(emp);
    this.customerController = Factory.createCustomerController(emp);
  }

  /** Displays the menu and redirects accordingly */
  public void menu() {

    String choice;
    do {
      System.out.println("Menu: ");
      System.out.println("0. Logout");
      System.out.println("1. Billing");
      System.out.println("2. View all Products");
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
            //            viewAllProducts();
            new ViewAllProducts().viewAllProducts(new CallBackImpl(emp));
            break;
          default:
            System.out.println("Enter a valid option");
            break;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } while (true);
  }

  /** Displays all the products in the inventory. */
  private void viewAllProducts() {
    //    System.out.println(inventoryController.getAllProducts());
  }

  /**
   * Helper method to create a new Bill
   *
   * @throws IOException invalid input
   */
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
        System.out.println("Enter the customer Id: ");
        String customerId = Globals.input.readLine();
        try {
          if (customerController.checkCustomerId(Integer.parseInt(customerId))) {
            generateBill(Integer.parseInt(customerId));
            return;
          }
        } catch (NumberFormatException e) {
          System.out.println("Enter a valid Number");
        } catch (ValidationException e) {
          System.out.println(e.getMessage());
        }
        System.out.println("Enter a valid Customer Id");
        break;
      default:
        System.out.println("Enter a valid input");
    }
  }

  /**
   * Adds a new Customer
   *
   * @throws IOException invalid input
   */
  private void newCustomer() throws IOException {

    System.out.println("Enter the details of new customer");
    String name;
    String address;

    do {
      System.out.println("Enter Customer Name: ");
      name = Globals.input.readLine();
      if (name.equals("0")) return;
      System.out.println("Enter the Customer Address: ");
      address = Globals.input.readLine();
      if (address.equals("0")) return;
      try {
        int customerId = customerController.addNewCustomer(name, address);
        generateBill(customerId);
        return;
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }
    } while (true);
  }

  /**
   * Generates a new bill
   *
   * @param customerId ID of the customer
   * @throws IOException raised when invalid input
   */
  private void generateBill(int customerId) throws IOException, ValidationException {

    BillDetails bill;
    try {
      bill = billController.newBill(customerId, emp.getId(), emp.getName());
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
      return;
    }

    do {
      System.out.println("Menu: ");
      System.out.println("1. Add Product");
      System.out.println("2. Cancel Bill");
      if (!billController.isBillEmpty(bill.getId())) {
        System.out.println("3. Remove Product");
        System.out.println("4. Payment");
      }
      String option = Globals.input.readLine();
      switch (option) {
        case "1":
          bill = addProduct(bill);
          break;
        case "2":
          System.out.println("Do you want to cancel bill?(y/n)");
          String in = Globals.input.readLine();
          if (in.equalsIgnoreCase("y")) {
            billController.cancelBill(bill.getId());
            return;
          }
          break;
        case "3":
          if (billController.isBillEmpty(bill.getId())) {
            System.out.println("Enter a valid input");
            break;
          }
          bill = removeProduct(bill);
          break;
        case "4":
          if (billController.isBillEmpty(bill.getId())) {
            System.out.println("Enter a valid input");
            break;
          }
          System.out.println("Proceed to the cashier for bill Payment");
          return;
        default:
          System.out.println("Enter a valid option");
      }
    } while (true);
  }

  /**
   * Adds new product to the bill
   *
   * @param bill current bill
   * @return the updated bill
   * @throws IOException invalid input
   */
  private BillDetails addProduct(BillDetails bill) throws IOException {

    String id;
    do {
      System.out.println("Enter Product id: (Enter 0 to exit)");
      id = Globals.input.readLine();
      if (id.equals("0")) return bill;

      System.out.println("Enter the quantity: (Enter 0 to exit) ");
      String quantity = Globals.input.readLine();
      if (quantity.equals("0")) return bill;

      try {
        BillDetails temp =
            billController.addProduct(bill, Integer.parseInt(id), Integer.parseInt(quantity));
        System.out.println(billController.getBill(bill.getId()));
        return temp;
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid Number");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }
    } while (true);
  }

  /**
   * Removes products from the bill
   *
   * @param bill current bill
   * @return the updated bill
   * @throws IOException invalid input
   */
  private BillDetails removeProduct(BillDetails bill) throws IOException {

    String number;
    String quantity;
    do {
      System.out.println("Enter the product id:");
      number = Globals.input.readLine();
      if (number.equals("0")) return bill;
      System.out.println("Enter the quantity to be removed");
      quantity = Globals.input.readLine();
      if (quantity.equals("0")) return bill;
      try {
        BillDetails temp =
            billController.removeProduct(
                bill, Integer.parseInt(number), Integer.parseInt(quantity));
        System.out.println("The quantity has been removed");
        System.out.println(billController.getBill(bill.getId()));
        return temp;
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid Number");
      }
    } while (true);
  }
}
