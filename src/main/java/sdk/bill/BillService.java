package sdk.bill;


import sdk.inventory.InventoryItem;
import sdk.inventory.InventoryRepository;
import sdk.inventory.InventoryService;
import sdk.util.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class BillService {

  private final BillRepository billRepository;
  private final InventoryRepository inventoryRepository;
  private final InventoryService inventoryService;
//  private final CustomerService customerService;
//  private final CustomerRepository customerRepository;

  public BillService() {
    billRepository = BillRepository.getInstance();
    inventoryRepository = InventoryRepository.getInstance();
    inventoryService = new InventoryService();
//    customerService = new CustomerService();
//    customerRepository = CustomerRepository.getInstance();

  }

  public int newBill(int employeeId, String employeeName, int customerId){
//
//    if (customerService.checkCustomerId(customerId))
//      throw new ValidationException("The customer id is invalid.");

    Bill bill = new Bill(employeeId, employeeName, customerId);
    billRepository.addNewBill(bill);
    return bill.getId();
  }

  public void addItem(int billId, int productId, int productQuantity) throws ValidationException {

    InventoryItem item = inventoryRepository.getProductById(productId);

    Bill bill = billRepository.getBill(billId);

    if(bill == null)
      throw new ValidationException("invalid Bill id");
    if (item == null) {
      throw new ValidationException("Invalid product id. Try again");
    }

    if (item.getQuantity() < productQuantity)
      throw new ValidationException(
          "Quantity not available. Available quantity is " + item.getQuantity());

    if (bill.checkIfItemIsPresent(productId)) {
      bill.setQuantity(productId, bill.getQuantity(productId) + productQuantity);
    } else {
      bill.addNewItem(item.getProduct(), productQuantity);
    }
    inventoryService.decreaseStock(item.getProduct().getId(), productQuantity);
  }

  public String displayBill(int billId) throws ValidationException {
    Bill bill = billRepository.getBill(billId);
    if(bill == null)
      throw new ValidationException("Enter a valid Bill Id");
    return bill.toString();
  }

  //TODO
  public boolean removeProduct(int billId, int productId, int productQuantity) throws ValidationException {
    Bill bill = billRepository.getBill(billId);
    if(bill == null)
      throw new ValidationException("Enter a valid Bill id");

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
  //TODO
  public void cancelBill(int billId) throws ValidationException {
    Bill bill = billRepository.getBill(billId);

    if(bill == null)
      throw new ValidationException("Bill Id is invalid.");
    for (BillItem item : bill.getBillItems()) {
      inventoryService.increaseStock(item.getProduct().getId(), item.getQuantity());
    }
    billRepository.removeBill(billId);
  }


  public boolean billSize(int billId) throws ValidationException {
    Bill bill = billRepository.getBill(billId);
    if(bill == null)
      throw new ValidationException("The bill Id is invalid");

    return bill.getBillSize() == 0;
  }

  public int getNoOfBills(int customerId) {
    List<Bill> bills = billRepository.getAllBillsOfCustomer(customerId);
    return bills.size();
  }

  //TODO
  public Double getTotalPurchaseOfCustomer(int customerId) {
    double amount = 0.0;
    List<Bill> ls = billRepository.getAllBillsOfCustomer(customerId);
    for (Bill bill : ls) {
        amount += bill.getAmount();
    }
    return amount;
  }

  public List<String> getCustomerBill(int customerId) {
    List<Bill> ls = billRepository.getAllBillsOfCustomer(customerId);
    List<String> billsString = new ArrayList<>();
    for (Bill bill : ls) {
      billsString.add(bill.toString());
    }
    return billsString;
  }

  public List<String> displayAllBills() {

    List<Bill> ls = billRepository.getAllBills();
    List<String> billsString = new ArrayList<>();
    for (Bill bill : ls) {
      billsString.add(bill.toString());
    }
    return billsString;

  }

  public boolean isPaid(int billId) throws ValidationException {
    Bill bill = billRepository.getBill(billId);
    if (bill == null) throw new ValidationException("Invalid Bill Id.");
    return bill.isPaid();
  }

  public void confirmPayment(int billId) throws ValidationException {
    Bill bill = billRepository.getBill(billId);
    if (bill == null) throw new ValidationException("Invalid Bill id");
    bill.changePaid();
  }
}
