package sdk.bill.repository;

import sdk.bill.domain.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillRepositoryImpl implements BillRepository {

  private final List<Bill> bills;
  private static BillRepositoryImpl instance;

  public static BillRepositoryImpl getInstance() {
    if (instance == null) instance = new BillRepositoryImpl();
    return instance;
  }

  public BillRepositoryImpl() {
    this.bills = new ArrayList<>();
  }

  @Override
  public List<Bill> getAllBills() {
    return bills;
  }

  @Override
  public List<Bill> getAllBillsOfCustomer(int customerId) {
    List<Bill> ls = new ArrayList<>();
    for (Bill bill : bills) {
      if (bill.getCustomerId() == customerId) {
        ls.add(bill);
      }
    }
    return ls;
  }

  @Override
  public Bill getBillById(int billId) {
    for (Bill bill : bills) {
      if (bill.getId() == billId) return bill;
    }
    return null;
  }

  @Override
  public void addNewBill(Bill bill) {
    bills.add(bill);
  }

  @Override
  public boolean removeBill(int billId) {
    return bills.removeIf(bill -> bill.getId() == billId);
  }
}
