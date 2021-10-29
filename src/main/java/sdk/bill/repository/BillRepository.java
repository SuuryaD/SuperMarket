package sdk.bill.repository;

import sdk.bill.domain.Bill;

import java.util.List;

public interface BillRepository {

  List<Bill> getAllBills();

  List<Bill> getAllBillsOfCustomer(int customerId);

  Bill getBillById(int billId);

  void addNewBill(Bill bill);

  boolean removeBill(int billId);
}
