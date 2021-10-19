package main;

import employee.EmployeeController;
import util.Globals;

import java.io.IOException;

/** Main class where the program execution starts. */
public class SuperMarket {

  /**
   * gets the username and pass from user and redirects accordingly
   *
   * @param args command line args
   */
  public static void main(String[] args) {

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
      int type = new EmployeeController().authenticate(username, pass);
      if (type == 0) {

        System.out.println("Credentials incorrect");
        continue;
      }

      if (type == 1) {
        new Admin().menu();
      } else {
        new Cashier().menu();
      }
    } while (true);
  }
}
