package controller;

import bill.Bill;
import bill.BillItem;
import inventory.Inventory;
import inventory.InventoryItem;

import java.util.List;

/**
 * class contains all the functionality that can be performed on bill
 */
public class BillController {

    private final Inventory inventory;

    public BillController(){
        this.inventory = Inventory.getInstance();
    }

    /**
     * updates the quantity of an item in bill if it already exists
     * or adds a new item to the bill.
     * @param bill bill to which the item has to be added.
     * @param item the item that has to be added
     * @param quantity the quantity of the item to be added
     */
    public void addItem(Bill bill, InventoryItem item, int quantity) {

        for(BillItem bItem : bill.getBillItems()){
            if(bItem.getProduct().getId() == item.getProduct().getId()){

                bItem.setQuantity(bItem.getQuantity() + quantity);
                inventory.reduceStock(item.getProduct().getId(), quantity);
                bill.setAmount(bill.getAmount() + bItem.getPrice());
                return;

            }
        }

        BillItem billItem = new BillItem(item.getProduct(), quantity);
        bill.getBillItems().add(billItem);
        bill.setAmount(bill.getAmount() + billItem.getPrice());
        inventory.reduceStock(item.getProduct().getId(), quantity);

    }

    /**
     * Removes a product from the bill.
     * @param bill bill from which the product has to be removed
     * @param itemNo the position of bill item in the bill list.
     * @param quantity the quantity to be removed.
     */
    public void removeProduct(Bill bill, int itemNo, int quantity){

        List<BillItem> billItems = bill.getBillItems();
        if(billItems.get(itemNo - 1).getQuantity() == quantity){

            inventory.updateStock(billItems.get(itemNo - 1).getProduct().getId(), billItems.get(itemNo - 1).getQuantity());
            bill.setAmount(bill.getAmount() - billItems.get(itemNo - 1).getPrice());
            billItems.remove(itemNo - 1);

        }
        else{
            inventory.updateStock(billItems.get(itemNo - 1).getProduct().getId(), quantity);
            billItems.get(itemNo - 1).setQuantity(billItems.get(itemNo - 1).getQuantity() - quantity);
            bill.setAmount(bill.getAmount() - billItems.get(itemNo - 1).getProduct().getPrice() * quantity);
        }
    }

    /**
     * Cancels the bill and updates the inventory.
     * @param bill bill which has to be cancelled.
     */
    public  void cancelBill(Bill bill){

        for(BillItem item : bill.getBillItems()){
            inventory.updateStock(item.getProduct().getId(), item.getQuantity());
        }
        bill.getBillItems().clear();
    }
}