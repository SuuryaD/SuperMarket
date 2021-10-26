package main;

import sdk.employee.*;

import java.io.IOException;

public class LoginUI {

    private final EmployeeController employeeController;

    public LoginUI(){
        employeeController = new EmployeeController();
    }


    public void run(){
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
                Employee emp = employeeController.authenticate(username, pass);
                emp.redirect(new Redirect());
                employeeController.logout();
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
