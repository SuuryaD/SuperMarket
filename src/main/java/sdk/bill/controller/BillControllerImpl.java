package sdk.bill.controller;

import sdk.bill.BillDetails;
import sdk.bill.service.BillService;
import sdk.customer.controller.CustomerValidation;
import sdk.employee.controller.EmployeeValidation;
import sdk.employee.domain.Employee;
import sdk.inventory.controller.InventoryValidation;
import sdk.util.Factory;
import sdk.util.ValidationException;

import java.util.List;

/** Implementation class of the bill controller. */
public class BillControllerImpl implements BillController {

  private final BillService billService;
  private final BillValidation billValidation;
  private final InventoryValidation inventoryValidation;
  private final CustomerValidation customerValidation;
  private final EmployeeValidation employeeValidation;
  private final Employee currentEmployee;

  public BillControllerImpl(Employee employee) {
    this.currentEmployee = employee;
    this.billService = Factory.createBillService();
    billValidation = Factory.createBillValidation();
    inventoryValidation = Factory.createInventoryValidation();
    customerValidation = Factory.createCustomerValidation();
    employeeValidation = Factory.createEmployeeValidation();
  }

  /**
   * Creates a new Bill
   *
   * @param customerId id of the customer
   * @param employeeId id of the billing employee
   * @param employeeName name of the billing employee.
   * @return Read only object with all bill details.
   * @throws ValidationException when input is invalid
   */
  //TODO get emp obj
  @Override
  public BillDetails newBill(int customerId, int employeeId, String employeeName)
      throws ValidationException {
    customerValidation.validateCustomerId(customerId);
    employeeValidation.validateEmployeeId(employeeId);
    return billService.newBill(employeeId, employeeName, customerId);
  }

  /**
   * Does all the validation of the input and redirects to adding product to bill
   *
   * @param bill current bill
   * @param productId id of the product to be added
   * @param productQuantity quantity to be added
   * @return Read only object with all bill details.
   * @throws ValidationException when input is invalid
   */
  @Override
  public BillDetails addProduct(BillDetails bill, int productId, int productQuantity)
      throws ValidationException {

    billValidation.validateBillId(bill.getId());
    inventoryValidation.validateProductId(productId);
//    billValidation.validateProductId(productId);
//    billValidation.validateProductQuantityToBeAdded(productId, productQuantity);
    inventoryValidation.validateProductQuantityToBeAdded(productId, productQuantity);
    return billService.addItem(bill, productId, productQuantity);
  }

  /**
   * Validates and redirects to getting the bill
   *
   * @param billId id of the bill to be fetched
   * @return Read only object with all bill details.
   * @throws ValidationException when inputs are invalid.
   */
  @Override
  public BillDetails getBill(int billId) throws ValidationException {
    billValidation.validateBillId(billId);
    return billService.getBill(billId);
  }

  /**
   * Validates input and redirects to removing product form bill
   *
   * @param bill current bill
   * @param productId id of the product to be removed
   * @param productQuantity quantity to be removed.
   * @return Read only object with all bill details.
   * @throws ValidationException when inputs are invalid
   */
  @Override
  public BillDetails removeProduct(BillDetails bill, int productId, int productQuantity)
      throws ValidationException {
    billValidation.validateBillId(bill.getId());
    inventoryValidation.validateProductId(productId);
    billValidation.validateProductQuantityToBeRemoved(bill.getId(), productId, productQuantity);
    return billService.removeItem(bill, productId, productQuantity);
  }

  /**
   * validates and redirects to cancelling bill
   *
   * @param billId id of the bill to be cancelled
   * @throws ValidationException when inputs are invalid
   */
  @Override
  public void cancelBill(int billId) throws ValidationException {
    billValidation.validateBillId(billId);
    billService.cancelBill(billId);
  }

  /**
   * validates input and redirects to checking if bill is empty
   *
   * @param billId id of the bill
   * @return true if bill is empty
   * @throws ValidationException when inputs are invalid.
   */
  @Override
  public boolean isBillEmpty(int billId) throws ValidationException {
    billValidation.validateBillId(billId);
    return billService.isBillEmpty(billId);
  }

  /**
   * gets all the bill
   *
   * @return list of Read only object with all bill details.
   */
  @Override
  public List<BillDetails> getAllBills() {
    return billService.getAllBills();
  }

  /**
   * validates and checks if the bill is paid
   *
   * @param billId id of the bill
   * @return true if paid.
   * @throws ValidationException when inputs are invalid
   */
  @Override
  public boolean isPaid(int billId) throws ValidationException {
    billValidation.validateBillId(billId);
    return billService.isPaid(billId);
  }
}
