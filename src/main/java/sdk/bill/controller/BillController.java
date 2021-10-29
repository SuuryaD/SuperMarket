package sdk.bill.controller;

import sdk.bill.BillDetails;
import sdk.bill.domain.Bill;
import sdk.util.ValidationException;

import java.util.List;

public interface BillController {
    BillDetails newBill(int customerId, int employeeId, String employeeName) throws ValidationException;

    BillDetails addProduct(BillDetails bill, int productId, int productQuantity)
            throws ValidationException;

    BillDetails getBill(int billId) throws ValidationException;

    BillDetails removeProduct(BillDetails bill, int productId, int productQuantity)
            throws ValidationException;

    void cancelBill(int billId) throws ValidationException;

    boolean isBillEmpty(int billId) throws ValidationException;

    List<BillDetails> getAllBills();

    boolean isPaid(int billId) throws ValidationException;
}
