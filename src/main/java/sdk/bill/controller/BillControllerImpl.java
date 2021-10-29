package sdk.bill.controller;

import sdk.bill.BillDetails;
import sdk.bill.service.BillService;
import sdk.employee.domain.Employee;
import sdk.util.Factory;
import sdk.util.ValidationException;

import java.util.List;

public class BillControllerImpl implements BillController {

  private final BillService billService;
  private final BillValidation billValidation;
  private final Employee currentEmployee;


  public BillControllerImpl(Employee employee) {
    this.currentEmployee = employee;
    this.billService = Factory.createBillService();
    billValidation = new BillValidation();
  }

  @Override
  public BillDetails newBill(int customerId, int employeeId, String employeeName) throws ValidationException {
    billValidation.validateCustomerId(customerId);
    return billService.newBill(
        employeeId, employeeName, customerId);
  }

  @Override
  public BillDetails addProduct(BillDetails bill, int productId, int productQuantity)
      throws ValidationException {

    billValidation.validateBillId(bill.getId());
    billValidation.validateProductId(productId);
    billValidation.validateProductQuantityToBeAdded(productId, productQuantity);
    return billService.addItem(bill, productId, productQuantity);
  }

  @Override
  public BillDetails getBill(int billId) throws ValidationException {
    billValidation.validateBillId(billId);
    return billService.getBill(billId);
  }


  @Override
  public BillDetails removeProduct(BillDetails bill, int productId, int productQuantity)
      throws ValidationException {
    billValidation.validateBillId(bill.getId());
    billValidation.validateProductId(productId);
    billValidation.validateProductQuantityToBeRemoved(bill.getId(), productId, productQuantity);
    return billService.removeItem(bill, productId, productQuantity);
  }

  @Override
  public void cancelBill(int billId) throws ValidationException {
    billValidation.validateBillId(billId);
    billService.cancelBill(billId);
  }

  @Override
  public boolean isBillEmpty(int billId) throws ValidationException {
    billValidation.validateBillId(billId);
    return billService.isBillEmpty(billId);
  }

  @Override
  public List<BillDetails> getAllBills() {
    return billService.getAllBills();
  }

  @Override
  public boolean isPaid(int billId) throws ValidationException {
    billValidation.validateBillId(billId);
    return billService.isPaid(billId);
  }

}
