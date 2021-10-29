package sdk.bill.service;

import sdk.bill.BillDetails;
import sdk.bill.domain.Bill;
import sdk.util.ValidationException;

import java.util.List;

public interface BillService {
  BillDetails newBill(int employeeId, String employeeName, int customerId);

  BillDetails addItem(BillDetails bill, int productId, int productQuantity) throws ValidationException;

  BillDetails getBill(int billId) throws ValidationException;

  BillDetails removeItem(BillDetails bill, int productId, int productQuantity) throws ValidationException;

  void cancelBill(int billId) throws ValidationException;

  boolean isBillEmpty(int billId) throws ValidationException;

  List<BillDetails> getCustomerBills(int customerId);

  List<BillDetails> getAllBills();

  boolean isPaid(int billId) throws ValidationException;

  void confirmBillPayment(int billId) throws ValidationException;

  Bill getBillById(int billId);
}
