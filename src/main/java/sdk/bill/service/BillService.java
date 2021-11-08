package sdk.bill.service;

import sdk.bill.BillDetails;
import sdk.bill.domain.Bill;
import sdk.util.ValidationException;

import java.util.List;

/**
 * Service layer of the bill domain
 */
public interface BillService {
  BillDetails newBill(int employeeId, String employeeName, int customerId);

  BillDetails addItem(BillDetails bill, int productId, int productQuantity)
      ;

  BillDetails getBill(int billId) ;

  BillDetails removeItem(BillDetails bill, int productId, int productQuantity)
      ;

  void cancelBill(int billId) ;

  boolean isBillEmpty(int billId) ;

  List<BillDetails> getCustomerBills(int customerId);

  List<BillDetails> getAllBills();

  boolean isPaid(int billId);

  void confirmBillPayment(int billId);

  Bill getBillById(int billId);
}
