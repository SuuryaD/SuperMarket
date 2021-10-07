package bill;

import inventory.Inventory;
import inventory.InventoryItem;
import util.Globals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Bill {
    private static int NUMBER = 1;
    private final int id;
    private final Date currentDate;
    private final List<BillItem> billItems;
    private double amount;
    private final int  employeeId;
    private final String employeeName;

    public Bill(int employeeId, String name){
        id = NUMBER;
        NUMBER++;
        currentDate = new Date();
        billItems = new ArrayList<>();
        amount = 0.0;
        this.employeeId = employeeId;
        this.employeeName = name;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void addItem(InventoryItem item, int quantity){

        for(BillItem bItem : billItems){
            if(bItem.getProduct().getId() == item.getProduct().getId()){
                bItem.setQuantity(bItem.getQuantity() + quantity);
                Inventory.reduceStock(item.getProduct().getId(), quantity);
                amount += bItem.getPrice();
                return;
            }
        }
        BillItem billItem = new BillItem(item.getProduct(), quantity);
        billItems.add(billItem);
        amount += billItem.getPrice();
        Inventory.reduceStock(item.getProduct().getId(), quantity);

    }

    public void removeProduct(int itemNo){

        Inventory.updateStock(billItems.get(itemNo - 1).getProduct().getId(), billItems.get(itemNo - 1).getQuantity());
        amount -= billItems.get(itemNo - 1).getPrice();
        billItems.remove(itemNo - 1);

    }

    public void displayBill(){


        DateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
        System.out.println("Bill ID: " + id + "\t Time: " + sdf.format(currentDate));
        System.out.println("Employee Id: " + employeeId + "\t Employee Name: " + employeeName);

        ArrayList<String> headers = new ArrayList<>();
        headers.add("Item No");
        headers.add("Name");
        headers.add("Quantity");
        headers.add("MRP");
        headers.add("Price");

        ArrayList<ArrayList<String>> content = new ArrayList<>();
        int no = 0;
        for (BillItem billItem : billItems) {
            no++;
            content.add(new ArrayList<>(Arrays.asList(String.valueOf(no),
                    billItem.getProduct().getName(),
                    String.valueOf(billItem.getQuantity()),
                    String.valueOf(billItem.getProduct().getPrice()),
                    String.valueOf(billItem.getPrice())
            )));
        }
        Globals.printTable(headers, content);
        System.out.println("Total Amount: " + Math.round(amount * 100.0)/100.0);
    }

    public  void cancelBill(){

        for(BillItem item : billItems){
            Inventory.updateStock(item.getProduct().getId(), item.getQuantity());
        }
        billItems.clear();
    }

}
