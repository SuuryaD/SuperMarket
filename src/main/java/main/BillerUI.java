package main;

import sdk.bill.BillController;
import sdk.customer.CustomerController;
import sdk.inventory.InventoryController;
import sdk.util.ValidationException;

import java.io.IOException;

/** Contains all the functionality of the user employee. */
public class BillerUI {

  private final InventoryController inventoryController;
  private final BillController billController;
  private final CustomerController customerController;

  public BillerUI() {

    this.inventoryController = new InventoryController();
    this.billController = new BillController();
    this.customerController = new CustomerController();

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
            viewAllBills();
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

  private void viewAllBills(){
    System.out.println(inventoryController.getAllProducts());
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
        break;
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
      int billId;
//      try{
      billId = billController.newBill(customerId);
//      }catch (ValidationException e){
//        System.out.println(e.getMessage());
//        return;
//      }

    do {
      System.out.println("Menu: ");
      System.out.println("1. Add Product");
      System.out.println("2. Cancel Bill");
      if (!billController.isBillEmpty(billId)) {
        System.out.println("3. Remove Product");
        System.out.println("4. Payment");
      }
      String option = Globals.input.readLine();
      switch (option) {
        case "1":
          addProduct(billId);
          break;
        case "2":
          System.out.println("Do you want to cancel bill?(y/n)");
          String in = Globals.input.readLine();
          if (in.equalsIgnoreCase("y")) {
            billController.cancelBill(billId);
            return;
          }
          break;
        case "3":
          if (billController.isBillEmpty(billId)) {
            System.out.println("Enter a valid input");
            break;
          }
          removeProduct(billId);
          break;
        case "4":
          if (billController.isBillEmpty(billId)) {
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
   * Adds product to the bill
   *
   * @throws IOException input error
   * @throws NumberFormatException invalid input
   */
  private void addProduct(int billId) throws IOException {

    String id;
    do {
      System.out.println("Enter Product id: (Enter 0 to exit)");
      id = Globals.input.readLine();
      if (id.equals("0")) return;

      System.out.println("Enter the quantity: (Enter 0 to exit) ");
      String quantity = Globals.input.readLine();
      if (quantity.equals("0")) return;

      try {
        billController.addProduct(billId, Integer.parseInt(id), Integer.parseInt(quantity));
        System.out.println(billController.printBill(billId));
        break;
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid Number");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }
    } while (true);
  }

  /**
   * Removes product from the bill
   *
   * @throws IOException input error
   * @throws NumberFormatException invalid input
   */
  private void removeProduct(int billId) throws IOException {

    String number;
    String quantity;
    do {
      System.out.println("Enter the product id:");
      number = Globals.input.readLine();
      if (number.equals("0")) return;
      System.out.println("Enter the quantity to be removed");
      quantity = Globals.input.readLine();
      if (quantity.equals("0")) return;
      try {
        if (billController.removeProduct(billId, Integer.parseInt(number), Integer.parseInt(quantity))) {
          System.out.println("The quantity has been removed");
          System.out.println(billController.printBill(billId));
          break;
        } else {
          System.out.println("Something went wrong. Try again");
        }
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid Number");
      }

    } while (true);
  }
}
