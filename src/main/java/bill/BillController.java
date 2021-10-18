package bill;

import inventory.ValidationException;

public class BillController {

    private final BillService billService;

    public BillController() {
        this.billService = new BillService();
    }

    public void newBill(int employeeId, String employeeName){
        billService.newBill(employeeId,employeeName);
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
}
