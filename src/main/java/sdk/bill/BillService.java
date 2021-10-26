package sdk.bill;

import sdk.inventory.InventoryService;
import sdk.util.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class BillService {

  private final BillRepository billRepository;
  private final InventoryService inventoryService;

  public BillService() {
    billRepository = BillRepository.getInstance();
    inventoryService = new InventoryService();
  }

  public int newBill(int employeeId, String employeeName, int customerId) {

    Bill bill = new Bill(employeeId, employeeName, customerId);
    billRepository.addNewBill(bill);
    return bill.getId();
  }

  public void addItem(int billId, int productId, int productQuantity) throws ValidationException {

    Bill bill = billRepository.getBillById(billId);

    if (bill == null) throw new ValidationException("invalid Bill id");

    if (inventoryService.getProductQuantity(productId) < productQuantity)
      throw new ValidationException(
          "Quantity not available. Available quantity is "
              + inventoryService.getProductQuantity(productId));

    if (bill.checkIfItemIsPresent(productId)) {
      bill.setQuantity(productId, bill.getQuantity(productId) + productQuantity);
    } else {
      bill.addNewItem(inventoryService.getProduct(productId), productQuantity);
    }
    inventoryService.decreaseStock(inventoryService.getProduct(productId).getId(), productQuantity);
  }

  public String printBill(int billId) throws ValidationException {
    Bill bill = billRepository.getBillById(billId);
    if (bill == null) throw new ValidationException("Enter a valid Bill Id");
    return bill.toString();
  }

  // TODO
  public boolean removeItem(int billId, int productId, int productQuantity)
      throws ValidationException {

    Bill bill = billRepository.getBillById(billId);
    if (bill == null) throw new ValidationException("Enter a valid Bill id");

    for (BillItem item : bill.getBillItems()) {
      if (item.getProduct().getId() == productId) {

        if (item.getQuantity() == productQuantity) {
          inventoryService.decreaseStock(productId, productQuantity);
          bill.removeBillItem(item);
          return true;
        } else {
          inventoryService.decreaseStock(productId, productQuantity);
          bill.setQuantity(
              item.getProduct().getId(),
              bill.getQuantity(item.getProduct().getId()) - productQuantity);
          return true;
        }
      }
    }
    return false;
  }
  // TODO
  public void cancelBill(int billId) throws ValidationException {
    Bill bill = billRepository.getBillById(billId);

    if (bill == null) throw new ValidationException("Bill Id is invalid.");
    for (BillItem item : bill.getBillItems()) {
      inventoryService.increaseStock(item.getProduct().getId(), item.getQuantity());
    }
    billRepository.removeBill(billId);
  }

  public boolean isBillEmpty(int billId) throws ValidationException {
    Bill bill = billRepository.getBillById(billId);
    if (bill == null) throw new ValidationException("The bill Id is invalid");

    return bill.getBillSize() == 0;
  }

  public int getNoOfBillsOfCustomer(int customerId) {
    List<Bill> bills = billRepository.getAllBillsOfCustomer(customerId);
    return bills.size();
  }

  public Double getTotalPurchaseOfCustomer(int customerId) {
    double amount = 0.0;
    List<Bill> ls = billRepository.getAllBillsOfCustomer(customerId);
    for (Bill bill : ls) {
      amount += bill.getAmount();
    }
    return amount;
  }

  public List<String> printAllBillsOfCustomer(int customerId) {
    List<Bill> ls = billRepository.getAllBillsOfCustomer(customerId);
    List<String> billsString = new ArrayList<>();
    for (Bill bill : ls) {
      billsString.add(bill.toString());
    }
    return billsString;
  }

  public List<String> printAllBills() {

    List<Bill> ls = billRepository.getAllBills();
    List<String> billsString = new ArrayList<>();
    for (Bill bill : ls) {
      billsString.add(bill.toString());
    }
    return billsString;
  }

  public boolean isPaid(int billId) throws ValidationException {
    Bill bill = billRepository.getBillById(billId);
    if (bill == null) throw new ValidationException("Invalid Bill Id.");
    return bill.isPaid();
  }

  public void confirmBillPayment(int billId) throws ValidationException {
    Bill bill = billRepository.getBillById(billId);
    if (bill == null) throw new ValidationException("Invalid Bill id");
    bill.changePaid();
  }
}
