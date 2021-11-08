package sdk.bill.repository;

import sdk.bill.domain.Bill;

import java.util.List;

/**
 * Database handling for the bill table
 */
public interface BillRepository {

  List<Bill> getAllBills();

  List<Bill> getAllBillsOfCustomer(int customerId);

  Bill getBillById(int billId);

  void addNewBill(Bill bill);

  boolean removeBill(int billId);
}
