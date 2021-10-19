package bill;

import inventory.ValidationException;
import util.Globals;

import java.util.List;

public class BillController {

    private final BillService billService;

    public BillController() {
        this.billService = new BillService();
    }

    public void newBill(int customerId){
        billService.newBill(Globals.loggedIn.getId(), Globals.loggedIn.getUsername(), customerId);
    }

    public void addProductToBill(int id, int quantity) throws ValidationException {

        if(id < 0)
            throw new ValidationException("Invalid id");

        if(quantity < 0)
            throw new ValidationException("Invalid Quantity");

        billService.addItem(id,quantity);

    }

    public String displayBill() {

        return billService.displayBill();
    }

    public boolean removeProduct(int id, int quantity) throws ValidationException {


        if(id < 0)
            throw new ValidationException("Invalid id");

        if(quantity < 0)
            throw new ValidationException("Invalid Quantity");

        return billService.removeProduct(id, quantity);
    }

    public void cancelBill() {
        billService.cancelBill();
    }

    public void confirmBill() {
        billService.confirmBill();
    }

    public boolean isBillEmpty() {
        return billService.billSize();
    }




    public List<String> displayCustomerBill(int customerId){

        return billService.getCustomerBill(customerId);

    }

    public List<String> displayAllBills() {
        return billService.displayallBills();
    }
}
