package main;

import sdk.bill.controller.BillControllerImpl;
import sdk.employee.domain.Employee;
import sdk.util.ValidationException;

import java.io.IOException;

public class DeliveryUI {

  private final Employee emp;
  private final BillControllerImpl billController;

  public DeliveryUI(Employee emp) {
    this.emp = emp;
    billController = new BillControllerImpl(emp);
  }

  public void menu() {

    do {
      System.out.println("Menu:");
      System.out.println("0. Logout");
      System.out.println("1. Delivery");

      try {
        String choice = Globals.input.readLine();
        switch (choice) {
          case "0":
            return;
          case "1":
            giveDelivery();
            break;
          default:
            System.out.println("Enter a valid option");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } while (true);
  }

  private void giveDelivery() throws IOException {

    do {
      System.out.println("Enter the bill Id: ");
      String id = Globals.input.readLine();
      if (id.equals("0")) return;
      try {
        if (billController.isPaid(Integer.parseInt(id))) {
          System.out.println("The Bill is paid.");
          return;
        }
        System.out.println("The payment is not done.");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }
    } while (true);
  }
}
