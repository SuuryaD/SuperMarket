package sdk.bill.service;

import sdk.bill.BillDetails;
import sdk.bill.BillItemDetails;
import sdk.bill.domain.Bill;
import sdk.bill.domain.BillItem;
import sdk.bill.repository.BillRepository;
import sdk.inventory.service.InventoryService;
import sdk.util.Factory;
import sdk.util.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for the bill
 */
public class BillServiceImpl implements BillService {

  private final BillRepository billRepository;
  private final InventoryService inventoryService;

  public BillServiceImpl() {
    billRepository = Factory.createBillRepository();
    inventoryService = Factory.createInventoryService();
  }

  /**
   * generates a new bill
   * @param employeeId id of the billing employee
   * @param employeeName name of the billing employee
   * @param customerId id of the customer
   * @return read only object with bill details.
   */
  @Override
  public BillDetails newBill(int employeeId, String employeeName, int customerId) {

    Bill bill = new Bill(employeeId, employeeName, customerId);
    billRepository.addNewBill(bill);
    return convertBillToBillDetails(bill);
  }

  public Bill getBillById(int billId) {
    return billRepository.getBillById(billId);
  }

  /**
   * Adds item to the bill
   * @param billDetails current bill
   * @param productId id of the product to be added
   * @param productQuantity quantity of the product to be added
   * @return returns the updated bill
   *
   */
  @Override
  public BillDetails addItem(BillDetails billDetails, int productId, int productQuantity) {

    Bill bill = billRepository.getBillById(billDetails.getId());

    if (bill.checkIfItemIsPresent(productId)) {
      bill.setQuantity(productId, bill.getQuantity(productId) + productQuantity);
    } else {
      bill.addNewItem(inventoryService.getInventoryItem(productId).getProduct(), productQuantity);
    }
    inventoryService.decreaseStock(
        inventoryService.getInventoryItem(productId).getProduct().getId(), productQuantity);
    return convertBillToBillDetails(bill);
  }

  @Override
  public BillDetails getBill(int billId) {
    Bill bill = billRepository.getBillById(billId);
    return convertBillToBillDetails(bill);
  }

  /**
   * Removes an item from the bill
   * @param billDetails current bill
   * @param productId id of the product to be removed
   * @param productQuantity quantity of the product to be removed
   * @return updated bill
   */
  @Override
  public BillDetails removeItem(BillDetails billDetails, int productId, int productQuantity)
      {

    Bill bill = billRepository.getBillById(billDetails.getId());

    for (BillItem item : bill.getBillItems()) {
      if (item.getProduct().getId() == productId) {

        if (item.getQuantity() == productQuantity) {
          inventoryService.increaseStock(productId, productQuantity);
          bill.removeBillItem(item);
          return convertBillToBillDetails(bill);
        } else {
          inventoryService.increaseStock(productId, productQuantity);
          bill.setQuantity(
              item.getProduct().getId(),
              bill.getQuantity(item.getProduct().getId()) - productQuantity);
          return convertBillToBillDetails(bill);
        }
      }
    }
    return billDetails;
  }

  /**
   * Cancels the bills and updates the inventory
   * @param billId id of the bill
   */
  @Override
  public void cancelBill(int billId)  {

    Bill bill = billRepository.getBillById(billId);
    if(bill.isPaid())
      return;
    for (BillItem item : bill.getBillItems()) {
      inventoryService.increaseStock(item.getProduct().getId(), item.getQuantity());
    }
    billRepository.removeBill(billId);
  }

  @Override
  public boolean isBillEmpty(int billId) {
    Bill bill = billRepository.getBillById(billId);
    return bill.getBillSize() == 0;
  }

  /**
   * gets all bills of the cutomer
   * @param customerId id of the customer
   * @return list of customer bills.
   */
  @Override
  public List<BillDetails> getCustomerBills(int customerId) {
    List<Bill> ls = billRepository.getAllBillsOfCustomer(customerId);
    List<BillDetails> billDetails = new ArrayList<>();
    for (Bill bill : ls) {
      billDetails.add(convertBillToBillDetails(bill));
    }
    return billDetails;
  }

  @Override
  public List<BillDetails> getAllBills() {
    List<Bill> ls = billRepository.getAllBills();
    List<BillDetails> billDetails = new ArrayList<>();
    for (Bill bill : ls) {
      billDetails.add(convertBillToBillDetails(bill));
    }
    return billDetails;
  }

  @Override
  public boolean isPaid(int billId) {
    Bill bill = billRepository.getBillById(billId);
    return bill.isPaid();
  }

  @Override
  public void confirmBillPayment(int billId){
    Bill bill = billRepository.getBillById(billId);
    bill.changePaid();
  }

  private BillDetails convertBillToBillDetails(Bill bill) {

    List<BillItemDetails> billItemDetails = new ArrayList<>();

    for (BillItem item : bill.getBillItems()) {
      billItemDetails.add(
          new BillItemDetails(item.getProduct(), item.getQuantity(), item.getPrice()));
    }

    return new BillDetails(
        bill.getId(),
        bill.getBillingTime(),
        billItemDetails,
        bill.getEmployeeId(),
        bill.getEmployeeName(),
        bill.getCustomerId(),
        bill.getAmount(),
        bill.isPaid());
  }
}
