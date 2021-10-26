package sdk.bill;

import sdk.customer.CustomerService;
import sdk.util.Globals;
import sdk.util.ValidationException;

import java.util.List;

public class BillController {

    private final BillService billService;

    public BillController() {
        this.billService = new BillService();
    }

    public int newBill(int customerId) throws ValidationException {

        if(customerId <= 0)
            throw new ValidationException("Enter a valid customerId");

        return billService.newBill(Globals.currentEmployee.getId(), Globals.currentEmployee.getUsername(), customerId);
    }

    public void addProduct(int billId, int productId, int productQuantity) throws ValidationException {

        if(billId <= 0)
            throw new ValidationException("Invalid Bill id");

        if(productId <= 0)
            throw new ValidationException("Invalid product id");

        if(productQuantity <= 0)
            throw new ValidationException("Invalid Quantity");

        billService.addItem(billId, productId,productQuantity);

    }

    public String printBill(int billId) throws ValidationException {

        if(billId < 0)
            throw new ValidationException("Enter a valid Bill id.");
        return billService.printBill(billId);
    }

    public boolean removeProduct(int billId, int productId, int productQuantity) throws ValidationException {

        if(billId < 0)
            throw new ValidationException("Enter a valid Bill id");

        if(productId < 0)
            throw new ValidationException("Invalid product id");

        if(productQuantity < 0)
            throw new ValidationException("Invalid Quantity");

        return billService.removeItem(billId, productId, productQuantity);
    }

    public void cancelBill(int billId) throws ValidationException {
        if(billId < 0)
            throw new ValidationException("Enter a valid Bill Id");
        billService.cancelBill(billId);
    }

    public boolean isBillEmpty(int billId) throws ValidationException {
        if(billId <= 0){
            throw new ValidationException("Enter a valid Bill Id");
        }
        return billService.isBillEmpty(billId);
    }

    public List<String> displayCustomerBill(int customerId){
        return billService.printAllBillsOfCustomer(customerId);
    }

    public List<String> displayAllBills() {
        return billService.printAllBills();
    }

    public boolean isPaid(int billId) throws ValidationException {
        if(billId < 0)
            throw new ValidationException("Enter a valid id");
        return billService.isPaid(billId);
    }

}
