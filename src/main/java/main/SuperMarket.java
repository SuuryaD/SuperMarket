package main;

import sdk.employee.Biller;
import sdk.employee.Employee;
import sdk.employee.EmployeeController;
import sdk.employee.Manager;

import java.io.IOException;

/** Main class where the program execution starts. */
public class SuperMarket {

  /**
   * gets the username and password from user and redirects accordingly
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    new LoginUI().run();

//    String username;
//    String pass;
//    EmployeeController employeeController = new EmployeeController();
//    do {
//      try {
//        System.out.println("Enter the username: ");
//        username = Globals.input.readLine();
//
//        if (username.equals("0")) {
//          System.exit(1);
//        }
//
//        System.out.println("Enter the password: ");
//        pass = Globals.input.readLine();
//
//      } catch (IOException e) {
//        e.printStackTrace();
//        break;
//      }
//      try {
//        Employee emp = employeeController.authenticate(username, pass);
//        if (emp instanceof Manager) {
//          new ManagerUI().menu();
//          employeeController.logout();
//        } else if (emp instanceof Biller) {
//          new BillerUI().menu();
//          employeeController.logout();
//        } else if (emp instanceof sdk.employee.FloorStaff) {
//          new FloorStaffUI().menu();
//          employeeController.logout();
//        } else if (emp instanceof sdk.employee.Cashier) {
//          new CashierUI().menu();
//          employeeController.logout();
//        } else if (emp instanceof sdk.employee.Delivery) new DeliveryUI().menu();
//        employeeController.logout();
//
//      } catch (IllegalArgumentException e) {
//        System.out.println("Credentials incorrect");
//      }
//    } while (true);
  }
}
