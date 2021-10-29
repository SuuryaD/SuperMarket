package main;

import sdk.employee.domain.Employee;
import sdk.inventory.controller.InventoryController;
import sdk.util.ValidationException;

import java.io.IOException;

public class FloorStaffUI {

  private final InventoryController inventoryController;
  private final Employee emp;
  public FloorStaffUI(Employee emp) {
    inventoryController = new InventoryController(emp);
    this.emp = emp;
  }

  public void menu() {
    do {
      try {
        System.out.println("Menu: ");
        System.out.println("0. Logout");
        System.out.println("1. view all products");
        System.out.println("2. Check Product availability");
        String choice = Globals.input.readLine();
        switch (choice) {
          case "0":
            return;
          case "1":
            viewAllProducts();
            break;
          case "2":
            checkProductAvailability();
            break;
          default:
            System.out.println("Enter a valid option");
        }
      } catch (IOException e) {
        System.out.println("Enter a valid input");
      }

    } while (true);
  }

  private void checkProductAvailability() throws IOException {
    do {

      System.out.println("Enter the product id");
      String id = Globals.input.readLine();
      if (id.equals("0")) return;
      try {
        System.out.println(
            "The Available Quantity is "
                + inventoryController.isProductAvailable(Integer.parseInt(id)));
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid Number");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }

    } while (true);
  }

  private void viewAllProducts() {
    System.out.println(inventoryController.getAllProducts());
  }
}
