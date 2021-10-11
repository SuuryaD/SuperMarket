package main;

import employee.Employee;
import employee.EmployeeList;
import util.Globals;
import util.Initialization;

import java.io.IOException;

/**
 * Main class where the program execution starts.
 */
public class HyperMarket {

    /**
     * gets the username and pass from user and redirects accordingly
     * @param args command line args
     */
    public static void main(String[] args){

        Initialization.initialiseInventory();
        Initialization.initialiseEmployee();
        String username;
        String pass;
        try{

            do{
                System.out.println("Enter the username: ");
                username = Globals.input.readLine();

                System.out.println("Enter the password: ");
                pass = Globals.input.readLine();

                if(EmployeeList.authenticate(username, pass)){

                    Employee emp = EmployeeList.getEmployee(username);
                    if(emp == null){
                        System.out.println("Something went wrong");
                        continue;
                    }
                    if(String.valueOf(emp.isAdmin()).equals("ADMIN"))
                        new Admin(emp).menu();
                    else
                        new User(emp).menu();
                }
                else{
                    System.out.println("Username or password incorrect");
                }
            }while(true);

        } catch(IOException e){
            e.printStackTrace();
        }

    }

}
