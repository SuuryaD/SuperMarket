package bill;

import inventory.InventoryItem;
import inventory.InventoryRepository;
import inventory.InventoryService;
import inventory.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class BillService {

    private Bill currentBill;
    private BillRepository billRepository;
    private InventoryRepository inventoryRepository;
    private InventoryService inventoryService;

    public BillService() {
        billRepository = BillRepository.getInstance();
        inventoryRepository = InventoryRepository.getInstance();
        inventoryService = new InventoryService();
    }

    public void newBill(int employeeId, String employeeName, int customerId){
        currentBill = new Bill(employeeId, employeeName, customerId);
    }

    public void addItem(int id, int quantity) throws ValidationException {

        InventoryItem item = inventoryRepository.getProductById(id);

        if(item == null){
            throw new ValidationException("Invalid id. Try again");
        }

        if(item.getQuantity() < quantity)
            throw new ValidationException("Quantity not available. Available quantity is " + item.getQuantity());

        if(currentBill.checkIfItemIsPresent(id)){
            currentBill.setQuantity(id, currentBill.getQuantity(id) + quantity);

        }else{
            currentBill.addNewItem(item.getProduct(),quantity);
        }
        inventoryService.decreaseStock(item.getProduct().getId(), quantity);

    }

    public String displayBill(){
        return currentBill.toString();
    }

    public boolean removeProduct(int id, int quantity) {
        for(BillItem item : currentBill.getBillItems()){
            if(item.getProduct().getId() == id){
                if(item.getQuantity() == quantity){
                    inventoryService.decreaseStock(id, quantity);
                    currentBill.removeBillItem(item);
                    return true;
                }
                else{
                    inventoryService.decreaseStock(id, quantity);
                    currentBill.setQuantity(item.getProduct().getId(), currentBill.getQuantity(item.getProduct().getId()) - quantity);
                    return true;
                }
            }
        }
        return false;
    }

    public void cancelBill() {
    for (BillItem item : currentBill.getBillItems()){
        inventoryService.increaseStock(item.getProduct().getId(), item.getQuantity());
    }
        currentBill = null;
    }

    public void confirmBill() {
        billRepository.addBill(currentBill);
        currentBill = null;
    }

    public boolean billSize() {
        if(currentBill.getBillSize() == 0)
            return true;
        return false;
    }

    public int getNoOfBills(int customerId) {
        return billRepository.getNoOfBillsOfCustomer(customerId);
    }

    public int getTotalPurchaseOfCustomer(int customerId) {
        int amount = 0;
        List<Bill> ls = billRepository.getAllBills();
        for(Bill bill : ls){
            if(bill.getCustomerId() == customerId){
                amount += bill.getAmount();
            }
        }
        return amount;
    }

    public List<String> getCustomerBill(int customerId){
        List<Bill> ls = billRepository.getAllBillsOfCustomer(customerId);
        List<String> billsString  = new ArrayList<>();
        for(Bill bill : ls){
            billsString.add(bill.toString());
        }
        return billsString;
    }

    public List<String> displayallBills() {

        List<Bill> ls = billRepository.getAllBills();
        List<String> billsString = new ArrayList<>();
        for(Bill bill : ls){
            billsString.add(bill.toString());
        }
        return billsString;

    }
}
