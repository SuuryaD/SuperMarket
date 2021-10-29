package main;

import sdk.employee.controller.Authenticate;
import sdk.employee.domain.Employee;

import java.io.IOException;

public class LoginUI {

  public void run() {
    String username;
    String pass;
    do {
      try {
        System.out.println("Enter the username: ");
        username = Globals.input.readLine();

        if (username.equals("0")) {
          System.exit(1);
        }
        System.out.println("Enter the password: ");
        pass = Globals.input.readLine();

      } catch (IOException e) {
        e.printStackTrace();
        break;
      }
      try {
        Employee emp = new Authenticate().login(username, pass);
        emp.redirect(new Redirect());
        //                if (emp instanceof Manager) {
        //                    new ManagerUI().menu();
        //                    employeeController.logout();
        //                } else if (emp instanceof Biller) {
        //                    new BillerUI().menu();
        //                    employeeController.logout();
        //                } else if (emp instanceof sdk.employee.FloorStaff) {
        //                    new FloorStaffUI().menu();
        //                    employeeController.logout();
        //                } else if (emp instanceof sdk.employee.Cashier) {
        //                    new CashierUI().menu();
        //                    employeeController.logout();
        //                } else if (emp instanceof sdk.employee.Delivery) new DeliveryUI().menu();
        //                employeeController.logout();
        //                    check.redirect(emp);
      } catch (IllegalArgumentException e) {
        System.out.println("Credentials incorrect");
      }
    } while (true);
  }
}
