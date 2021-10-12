package main;

import employee.Employee;
import employee.EmployeeList;
import util.Globals;
import util.Utils;

import java.io.IOException;

/** Main class where the program execution starts. */
public class SuperMarket {

  /**
   * gets the username and pass from user and redirects accordingly
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    // because we don't have a datastore
    Utils.initializeTempInventory();
    Utils.initializeTempEmployeeList();
    Utils.initializeTempCustomerList();

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

      Employee emp = EmployeeList.getInstance().authenticate(username, pass);
      if (emp == null) {
        System.out.println("Credentials incorrect");
        continue;
      }

      if (emp.isAdmin()) {
        new Admin(emp).menu();
      } else {
        new Cashier(emp).menu();
      }
    } while (true);
  }
}
