package sdk.bill.controller;

import sdk.bill.domain.Bill;
import sdk.bill.service.BillService;
import sdk.inventory.service.InventoryServiceImpl;
import sdk.util.Factory;
import sdk.util.ValidationException;

public class BillValidation {

    private final BillService billService;
    private final InventoryServiceImpl inventoryService;

    public BillValidation(){
        billService = Factory.createBillService();
        inventoryService = new InventoryServiceImpl();
    }

    public void validateBillId(int billId) throws ValidationException {

        Bill bill = billService.getBillById(billId);
        if(bill == null)
            throw new ValidationException("The bill Id is invalid");
    }

    public void validateProductId(int productId) throws ValidationException {

        if(inventoryService.getInventoryItem(productId) == null){
            throw new ValidationException("Enter a valid product Id");
        }
    }

    public void validateProductQuantityToBeAdded(int productId, int quantity) throws ValidationException {
        if(quantity <= 0)
            throw new ValidationException("Enter a valid quantity");
        if(inventoryService.getProductQuantity(productId) < quantity)
            throw new ValidationException("The quantity available is " + inventoryService.getProductQuantity(productId));
    }

    public void validateCustomerId(int customerId) throws ValidationException {
        if(customerId <= 0)
            throw new ValidationException("Enter a valid Customer Id");
    }

    public void validateProductQuantityToBeRemoved(int billId, int productId, int productQuantity) throws ValidationException {

        if(productQuantity <= 0)
            throw new ValidationException("Enter a valid quantity");
        validateBillId(billId);
        validateProductId(productId);
        Bill bill = billService.getBillById(billId);
        if(bill.getQuantity(productId) < productQuantity)
            throw new ValidationException("Enter a valid quantity to be removed");
    }
}
