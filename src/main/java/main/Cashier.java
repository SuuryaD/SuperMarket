package main;

import bill.BillController;
import customer.CustomerController;
import employee.Employee;
import inventory.InventoryController;
import inventory.ValidationException;
import util.Globals;

import java.io.IOException;

/** Contains all the functionality of the user employee. */
public class Cashier {

  private final InventoryController inventoryController;
  private final BillController billController;
  private final CustomerController customerController;

  public Cashier() {

    this.inventoryController = new InventoryController();
    this.billController = new BillController();
    this.customerController = new CustomerController();
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
            //            generateBill(new Customer("surya", "abcdd"));
            break;

          case "2":
            System.out.println(inventoryController.getAllProducts());
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
        try {

          if (customerController.checkCustomerId(Integer.parseInt(id))) {
            generateBill(Integer.parseInt(id));
            return;
          }
        } catch (NumberFormatException e) {
          System.out.println("Enter a valid Number");
        } catch (ValidationException e) {
          System.out.println(e.getMessage());
        }
        System.out.println("Enter a valid Customer id");
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
      System.out.println("Enter the Customer Address: ");
      address = Globals.input.readLine();
      if (address.equals("0")) return;
      try {
        int id = customerController.addNewCustomer(name, address);
        generateBill(id);
        break;
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }

    } while (true);
//    do {
//      System.out.println("Enter Customer Name: ");
//      name = Globals.input.readLine();
//      if (name.equals("0")) return;
//      if (name.matches("^[a-zA-Z]+( [a-zA-Z]+)?$")) break;
//      else System.out.println("enter a valid name");
//    } while (true);
//
//    do {
//      System.out.println("Enter the Customer Address: ");
//      address = Globals.input.readLine();
//      if (address.equals("0")) return;
//      if (address.length() != 0) break;
//      System.out.println("Enter a valid address");
//
//    } while (true);
//
//    Customer customer = new Customer(name, address);
//    CustomerRepository.getInstance().addCustomer(customer);
//    generateBill(customer);
  }

  /**
   * Generates a new bill
   *
   * @param customer customer to which billing is done
   * @throws IOException raised when invalid input
   */
  private void generateBill(int customerId) throws IOException {

    billController.newBill(customerId);

    do {
      System.out.println("Menu: ");
      System.out.println("1. Add Product");
      System.out.println("2. Cancel Bill");
      if (!billController.isBillEmpty()) {
        System.out.println("3. Remove Product");
        System.out.println("4. Payment");
      }
      String option = Globals.input.readLine();
      switch (option) {
        case "1":
          addProduct();

          break;
        case "2":
          System.out.println("Do you want to cancel bill?(y/n)");
          String in = Globals.input.readLine();
          if (in.equalsIgnoreCase("y")) {
            billController.cancelBill();
            return;
          }
          break;
        case "3":
          if (billController.isBillEmpty()) {
            System.out.println("Enter a valid input");
            break;
          }
          removeProduct();
          break;
        case "4":
          if (billController.isBillEmpty()) {
            System.out.println("Enter a valid input");
            break;
          }

          if (new Payment().menu()) {
            System.out.println("Payment Successful");
            //            customer.addBill(bill);
            //            BillRepository.addBill(bill);
            billController.confirmBill();
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
   * @throws IOException input error
   * @throws NumberFormatException invalid input
   */
  private void addProduct() throws IOException {

    String id;
    do {
      System.out.println("Enter Product id: (Enter 0 to exit)");
      id = Globals.input.readLine();
      if (id.equals("0")) return;

      System.out.println("Enter the quantity: (Enter 0 to exit) ");
      String quantity = Globals.input.readLine();
      if (quantity.equals("0")) return;

      try {

        billController.addProductToBill(Integer.parseInt(id), Integer.parseInt(quantity));
        System.out.println(billController.displayBill());
        break;
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid Number");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }
    } while (true);

    //
    //    do {
    //      System.out.println("Enter Product id: (Enter 0 to exit)");
    //      id = Globals.input.readLine();
    //      if (id.equals("0")) return;
    //      try {
    ////        item = Inventory.getInstance().getProductById(Integer.parseInt(id));
    //        item = InventoryRepository.getProductById(Integer.parseInt(id));
    //      } catch (NumberFormatException e) {
    //        System.out.println("Enter a valid id.");
    //        continue;
    //      }
    //      if (item != null) break;
    //
    //      System.out.println("Product does not exist. Enter a valid Product ID");
    //    } while (true);
    //
    //    do {
    //      System.out.println("Enter the quantity: (Enter 0 to exit) ");
    //      String quantity = Globals.input.readLine();
    //
    //      if (quantity.equals("0")) return;
    //      int quantityInt;
    //
    //      try {
    //        quantityInt = Integer.parseInt(quantity);
    //      } catch (NumberFormatException e) {
    //        System.out.println("Enter a valid quantity");
    //        continue;
    //      }
    //
    //      if (quantityInt < 0) {
    //        System.out.println("Enter a valid quantity");
    //      } else if (item.getQuantity() < quantityInt) {
    //        System.out.println(
    //            "The requested quantity is not available. Available Quantity is " +
    // item.getQuantity());
    //      } else {
    //        bill.addItem(item, quantityInt);
    //        break;
    //      }
    //    } while (true);
  }

  /**
   * Removes product from the bill
   *
   * @throws IOException input error
   * @throws NumberFormatException invalid input
   */
  private void removeProduct() throws IOException {

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
        if (billController.removeProduct(Integer.parseInt(number), Integer.parseInt(quantity))) {
          System.out.println("The quantity has been removed");
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

    //    do {
    //      System.out.println("Enter the item No: ");
    //      number = Globals.input.readLine();
    //      if (number.equals("0")) return;
    //      try {
    //        if (bill.getBillItems().size() < Integer.parseInt(number) || Integer.parseInt(number)
    // < 0) {
    //          System.out.println("Enter a valid item Number.");
    //        } else break;
    //      } catch (NumberFormatException e) {
    //        System.out.println("Enter a valid number");
    //      }
    //    } while (true);
    //
    //    do {
    //      System.out.println("Enter the quantity to be removed");
    //      quantity = Globals.input.readLine();
    //
    //      if (quantity.equals("0")) return;
    //
    //      int qnt = bill.getBillItems().get(Integer.parseInt(number) - 1).getQuantity();
    //      int removeQuantity;
    //      try {
    //        removeQuantity = Integer.parseInt(quantity);
    //      } catch (NumberFormatException e) {
    //        System.out.println("Enter a valid number");
    //        continue;
    //      }
    //      if (qnt < removeQuantity || removeQuantity < 0)
    //        System.out.println("Invalid Quantity. Enter a valid quantity");
    //      else {
    //        bill.removeProduct(Integer.parseInt(number), Integer.parseInt(quantity));
    //        break;
    //      }
    //
    //    } while (true);
  }
}
