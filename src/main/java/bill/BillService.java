package bill;

import inventory.InventoryItem;
import inventory.InventoryRepository;

public class BillService {

    private Bill currentBill;

    public void newBill(int employeeId, String employeeName){
        currentBill = new Bill(employeeId, employeeName);
    }

    public void addItem(int id, int quantity) {

        InventoryItem item = InventoryRepository.getProductById(id);

        if(currentBill.checkIfItemIsPresent(id)){
            currentBill.setQuantity(id, currentBill.getQuantity(id) + quantity);
            InventoryRepository.reduceStock(item.getProduct().getId(), quantity);
        }else{
            currentBill.addNewItem(item.getProduct(),quantity);
            InventoryRepository.reduceStock(item.getProduct().getId(), quantity);
        }


//        for (BillItem bItem : billItems) {
//            if (bItem.getProduct().getId() == item.getProduct().getId()) {
//
//                bItem.setQuantity(bItem.getQuantity() + quantity);
////        Inventory.getInstance().reduceStock(item.getProduct().getId(), quantity);
//                InventoryRepository.reduceStock(item.getProduct().getId(), quantity);
//                amount += item.getProduct().getPrice() * quantity;
//                return;
//            }
//        }
//
//        BillItem billItem = new BillItem(item.getProduct(), quantity);
//        bill.addBillItem(billItem);
//        billItems.add(billItem);
//        amount += billItem.getPrice();
////    Inventory.getInstance().reduceStock(item.getProduct().getId(), quantity);
//        InventoryRepository.reduceStock(item.getProduct().getId(), quantity);

    }

    public String displayBill(){
        return currentBill.toString();
    }

}
