package main;

import bill.Bill;
import customer.Customer;
import customer.CustomerList;
import employee.Employee;
import employee.EmployeeList;
import inventory.Inventory;
import inventory.Product;
import util.Globals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains all the functionality of an admin
 */
public class Admin {

    private final Employee emp;

    public Admin(Employee emp) {
        this.emp = emp;
    }

    /**
     * Displays all the options for admin
     */
    public void menu(){

        String option;

        do{
            System.out.println("Admin Menu: ");
            System.out.println("0. Logout");
            System.out.println("1. View all employee");
            System.out.println("2. Add Employee");
            System.out.println("3. Remove Employee");
            System.out.println("4. View all Products ");
            System.out.println("5. Add New Product");
            System.out.println("6. Restock Existing Products");
            System.out.println("7. view all bills");
            System.out.println("8. View all customers");

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
                    case "4":
                        System.out.println(Inventory.getInstance());
                        break;
                    case "5":
                        addProduct();
                        break;
                    case "6":
                        restockProduct();
                        break;
                    case "7":
                        viewAllBills();
                        break;
                    case "8":
                        viewAllCustomers();
                        break;
                    default:
                        System.out.println("Enter a valid option");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }while(true);
    }

    /**
     * Adds a new employee to the employee list.
     * @throws IOException raised when invalid input is provided
     */
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

    /**
     * Removes an employee from employee list
     * @throws IOException raised when invalid input is provided
     */
    private void removeEmployee() throws IOException {

        String id;
        do{
            System.out.println("Enter the employee Id: ");
            id = Globals.input.readLine();

            if(id.equals("0"))
                return;
            if(Integer.parseInt(id) == emp.getId()){
                System.out.println("You cannot delete your own account");
                continue;

            }
            if(EmployeeList.removeEmployee(Integer.parseInt(id)))
                System.out.println("Employee Removed Successfully");
            else{
                System.out.println("Invalid employee id. Enter a valid employee id");
            }

        }while(true);

    }

    /**
     * Adds a new product to the inventory.
     * @throws IOException raised when invalid input is provided
     */
    private void addProduct() throws IOException {
        String id;
        do{
            System.out.println("Enter the product id");
            id = Globals.input.readLine();
            if(id.equals("0"))
                return;
            if(!Inventory.getInstance().checkIfProductIsAvailable(Integer.parseInt(id)))
                break;
            else
                System.out.println("The product id is already available. Try another id");

        }while(true);

        System.out.println("Enter the product name: ");
        String name = Globals.input.readLine();

        System.out.println("Enter the Product price: ");
        String price = Globals.input.readLine();

        System.out.println("Enter the product quantity: ");
        String quantity = Globals.input.readLine();

        Inventory.getInstance().add(new Product(Integer.parseInt(id), name, Double.parseDouble(price)), Integer.parseInt(quantity));

    }

    /**
     * Restocks the existing product in inventory
     * @throws IOException
     */
    private void restockProduct() throws IOException{
        String id;
        do{
            System.out.println("Enter the product id");
            id = Globals.input.readLine();
            if(id.equals("0"))
                return;
            if(Inventory.getInstance().checkIfProductIsAvailable(Integer.parseInt(id)))
                break;
            else
                System.out.println("No such Product available");
        }while(true);

        System.out.println("Enter the quantity");
        String quantity = Globals.input.readLine();

        Inventory.getInstance().updateStock(Integer.parseInt(id),Integer.parseInt(quantity) );
    }

    /**
     * Displays all the bills
     * @throws IOException input error.
     */
    private void viewAllBills() throws IOException {
        ArrayList<Bill> bills = new ArrayList<>();

        for(Customer customer : CustomerList.getCustomerList()){
            bills.addAll(customer.getBills());
        }
        if(bills.size() == 0){
            System.out.println("No bills available");
            return;
        }
        int i = 0;
        do{
            System.out.println(bills.get(i));
            System.out.println("1. Next");
            if(i > 0)
                System.out.println("2. Previous");
            System.out.println("3. exit");
            String option = Globals.input.readLine();
            switch (option){
                case "1":
                    i++;
                    break;
                case "2":
                    if(i > 0){
                        i--;
                    }else{
                        System.out.println("Enter a valid option");
                    }
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Enter a valid option");
            }
        }while(true);

    }

    /**
     * Displays all customer details in a tabular form
     */
    private void viewAllCustomers(){

       ArrayList<String> headers = new ArrayList<>();
       ArrayList<ArrayList<String >> content = new ArrayList<>();

       headers.add("Customer id");
       headers.add("Customer name");
       headers.add("No of purchases");
       headers.add("Total Purchase amount");

       for(Customer customer : CustomerList.getCustomerList()){

            content.add(new ArrayList<>(Arrays.asList(
                    String.valueOf(customer.getId()),
                    customer.getName(),
                    String.valueOf(customer.getBills().size()),
                    String.valueOf(customer.getTotalPurchase())
            )));
       }
       System.out.println(Globals.printTable(headers,content));

    }

}
