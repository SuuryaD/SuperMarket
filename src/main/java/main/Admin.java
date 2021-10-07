package main;

import employee.Employee;
import employee.EmployeeList;
import util.Globals;

import java.io.IOException;


public class Admin {

    private Employee emp;

    public Admin(Employee emp) {
        this.emp = emp;
    }

    public void menu(){

        String option;

        do{
            System.out.println("Admin Menu: ");
            System.out.println("0. Logout");
            System.out.println("1. View all employee");
            System.out.println("2. Add Employee");
            System.out.println("3. Remove Employee");

            try{
                option = Globals.input.readLine();
                switch (option){
                    case "0":
                        return;
                    case "1":
                        EmployeeList.displayAll();
                        break;
                    case "2":
                        addEmployee();
                        break;
                    case "3":
                        removeEmployee();
                        break;
                    default:
                        System.out.println("Enter a valid option");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }while(true);
    }


    private void addEmployee() throws IOException {

        System.out.println("Enter the Employee name: ");
        String name = Globals.input.readLine();
        String username;

        do{
            System.out.println("Enter the username for " + name);
            username = Globals.input.readLine();
            if(username.equals("exit"))
                return;
            if(EmployeeList.checkUsernameAvailability(username))
                break;
            else
                System.out.println("Username already taken. Enter other username");

        }while(true);

        System.out.println("Enter the password");
        String pass = Globals.input.readLine();
        System.out.println("Is a Admin? (y/n)");
        String choice = Globals.input.readLine();

        EmployeeList.addEmployee(name, username, pass, choice.equalsIgnoreCase("y"));

    }

    private void removeEmployee() throws IOException {

        System.out.println("Enter the employee Id: ");
        String id = Globals.input.readLine();
        if(EmployeeList.removeEmployee(Integer.parseInt(id)))
            System.out.println("Employee Removed Successfully");
        else{
            System.out.println("Invalid employee id. Enter a valid employee id");
        }

    }

}
