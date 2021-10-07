package main;

import bill.Bill;
import employee.Employee;
import inventory.Inventory;
import inventory.InventoryItem;
import org.jetbrains.annotations.NotNull;
import util.Globals;

import java.io.IOException;

public class User {

    private Employee employee;

    public User(Employee employee){
        this.employee = employee;
    }

    public void menu() {

        String choice;
        do{
            System.out.println("Menu:");
            System.out.println("1. Billing");
            System.out.println("0. Logout");
            System.out.println("Enter an option");
            try{
                choice = Globals.input.readLine();
                switch(choice){

                    case "0":
                        return;
                    case "1":
                        generateBill();
                        break;
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

    private void generateBill() throws IOException {

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

                    switch (choice){
                        case "0":
                            return;
                        case "1":
                            addProduct(bill);
                            bill.displayBill();
                            break;
                        case "2":
                            bill.cancelBill();
                            System.out.println("Your bill has been cancelled");
                            return;
                        default:
                            System.out.println("Enter a valid option");
                            break;
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

                    switch (option){
                        case "1":
                            addProduct(bill);
                            bill.displayBill();
                            break;
                        case "2":
                            removeProduct(bill);
                            bill.displayBill();
                            break;
                        case "3":
                            System.out.println("Redirecting to payment");
                            return;
                        case "4":
                            bill.cancelBill();
                            System.out.println("Your Bill has been cancelled.");
                            return;
                        default:
                            System.out.println("Enter a valid option");
                            bill.displayBill();
                    }
                    if(bill.getBillItems().isEmpty())
                        break;
                }while(true);

            }
        }while(true);
    }

    private void addProduct(Bill bill) throws IOException {
        InventoryItem item;

        String id;
        do{
            System.out.println("Enter Product id: ");
            id = Globals.input.readLine();
            item = Inventory.getProductById(Integer.parseInt(id));
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
                bill.addItem(item, Integer.parseInt(quantity));
                break;
            }
        }while(true);
    }

    private void removeProduct(@NotNull Bill bill) throws IOException {

        String number;
        do{
            System.out.println("Enter the item No: ");
            number = Globals.input.readLine();
            if(bill.getBillItems().size() < Integer.parseInt(number) || Integer.parseInt(number) <= 0){
                System.out.println("Enter a valid item Number.");
            }
            else
                break;
        }while(true);

        bill.removeProduct(Integer.parseInt(number));
    }

}
