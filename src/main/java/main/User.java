package main;

import bill.Bill;
import controller.BillController;
import customer.Customer;
import customer.CustomerList;
import employee.Employee;
import inventory.Inventory;
import inventory.InventoryItem;
import payment.Payment;
import util.Globals;

import java.io.IOException;

/**
 * Contains all the functionality of the user employee.
 */
public class User {

    private final Employee employee;

    private final BillController billController;

    public User(Employee employee){

        this.employee = employee;
        billController = new BillController();

    }

    /**
     * Displays the menu and redirects accordingly
     */
    public void menu() {

        String choice;
        do{
            System.out.println("Menu:");
            System.out.println("1. Billing");
            System.out.println("2. View all Products");
            System.out.println("0. Logout");
            System.out.println("Enter an option");
            try{
                choice = Globals.input.readLine();
                switch(choice){

                    case "0":
                        return;
                    case "1":
                        newBill();
                        break;
                    case "2":
                        System.out.println(Inventory.getInstance());
                    default:
                        System.out.println("Enter a valid option");
                        break;
                }
            }catch (IOException e){
                System.out.println("Internal error");
                System.exit(1);
            }
        }while(true);

    }

    private void newBill() throws IOException {

        System.out.println("1. New Customer");
        System.out.println("2. Existing Customer");
        String option = Globals.input.readLine();

        switch(option){
            case "1":
                newCustomer();
                return;
            case "2":
                System.out.println("Enter the customer id: ");
                String id = Globals.input.readLine();
                Customer customer = CustomerList.getCustomerById(Integer.parseInt(id));

                if(customer != null){
                    generateBill(customer);
                    return;
                }
            case "0":
                return;
            default:
                System.out.println("Enter a valid input");
        }

    }

    private void newCustomer() throws IOException {

        System.out.println("Enter Customer Name: ");
        String name = Globals.input.readLine();
        System.out.println("Enter the Customer Address: ");
        String address = Globals.input.readLine();
        Customer customer = new Customer(name, address);
        CustomerList.addCustomer(customer);
        generateBill(customer);

    }

    /**
     * Generates a new bill
     * @param customer customer to which billing is done
     * @throws IOException raised when invalid input
     */
    private void generateBill(Customer customer) throws IOException {

        Bill bill = new Bill(employee.getId(), employee.getName());

        String option;
        do{
            if(bill.getBillItems().isEmpty()){
                String choice;
                do{
                    System.out.println("Menu: ");
                    System.out.println("1. Add Product");
                    System.out.println("2. Cancel Bill");
                    choice = Globals.input.readLine();
                    try{
                        switch (choice){
                            case "0":
                                return;
                            case "1":
                                addProduct(bill);
                                System.out.println(bill);
                                break;
                            case "2":
                                billController.cancelBill(bill);
                                System.out.println("Your bill has been cancelled");
                                return;
                            default:
                                System.out.println("Enter a valid option");
                                break;
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Enter a valid Number");
                    }
                    if(!bill.getBillItems().isEmpty())
                        break;
                }while(true);
            }
            else{
                do{
                    System.out.println("Menu: ");
                    System.out.println("1. Add Product");
                    System.out.println("2. Remove Product");
                    System.out.println("3. Payment");
                    System.out.println("4. Cancel Bill");
                    option = Globals.input.readLine();
                    try{
                        switch (option){
                            case "1":
                                addProduct(bill);
                                System.out.println(bill);
                                break;
                            case "2":
                                removeProduct(bill);
                                System.out.println(bill);
                                break;
                            case "3":
                                if(new Payment().menu()){
                                    System.out.println("Payment Successful");
                                    customer.addBill(bill);
                                    return;
                                }
                                else{
                                    System.out.println("Payment unsuccessful");
                                    break;
                                }

                            case "4":
                                billController.cancelBill(bill);
                                System.out.println("Your Bill has been cancelled.");
                                return;
                            default:
                                System.out.println("Enter a valid option");
                                System.out.println(bill);
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Enter a valid Number");
                    }
                    if(bill.getBillItems().isEmpty())
                        break;
                }while(true);
            }
        }while(true);
    }

    /**
     * Adds product to the bill
     * @param bill bill to which product is to be added
     * @throws IOException input error
     * @throws NumberFormatException invalid input
     */
    private void addProduct(Bill bill) throws IOException, NumberFormatException {
        InventoryItem item;

        String id;
        do{
            System.out.println("Enter Product id: ");
            id = Globals.input.readLine();
            item = Inventory.getInstance().getProductById(Integer.parseInt(id));
            if(id.equals("exit"))
                return;
            if(item == null){
                System.out.println("Product does not exist. Enter a valid Product ID");
            }
            else
                break;
        }while(true);

        do{
            System.out.println("Enter the quantity: ");
            String quantity = Globals.input.readLine();
            if(quantity.equals("exit"))
                return;
            if(item.getQuantity() < Integer.parseInt(quantity)){
                System.out.println("The requested quantity is not available. Available Quantity is "+ item.getQuantity());
            }
            else{
                billController.addItem(bill, item, Integer.parseInt(quantity));
                break;
            }
        }while(true);
    }

    /**
     * Removes product from the bill
     * @param bill bill
     * @throws IOException input error
     * @throws NumberFormatException invalid input
     */
    private void removeProduct(Bill bill) throws IOException, NumberFormatException {

        String number;
        String quantity;
        do{
            System.out.println("Enter the item No: ");
            number = Globals.input.readLine();
            if(bill.getBillItems().size() < Integer.parseInt(number) || Integer.parseInt(number) <= 0){
                System.out.println("Enter a valid item Number.");
            }
            else
                break;
        }while(true);

        System.out.println("Enter the quantity to be removed");
        quantity = Globals.input.readLine();
        try{
            int qnt = bill.getBillItems().get(Integer.parseInt(number) - 1).getQuantity();
            if(qnt < Integer.parseInt(quantity) || Integer.parseInt(quantity) < 0)
                System.out.println("Invalid Quantity. Enter a valid quantity");
            else
                billController.removeProduct(bill, Integer.parseInt(number), Integer.parseInt(quantity));
        }catch (NumberFormatException e){
            System.out.println("Enter a valid Number");
        }
    }

}
