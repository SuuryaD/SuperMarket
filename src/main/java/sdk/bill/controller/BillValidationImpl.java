package sdk.bill.controller;

import sdk.bill.domain.Bill;
import sdk.bill.service.BillService;
import sdk.inventory.service.InventoryServiceImpl;
import sdk.util.Factory;
import sdk.util.ValidationException;

/** Bill validation implementation calss */
public class BillValidationImpl implements BillValidation {

  private final BillService billService;

  public BillValidationImpl() {
    billService = Factory.createBillService();
  }
  /**
   * Validates the bill id
   *
   * @param billId id of the bill
   * @throws ValidationException input is invalid
   */
  @Override
  public void validateBillId(int billId) throws ValidationException {
    Bill bill = billService.getBillById(billId);
    if (bill == null) throw new ValidationException("The bill Id is invalid");
  }

    /**
     * Validates if the product is present in the bill.
     * @param billId id of the bill
     * @param productId id of the product
     * @throws ValidationException
     */
  public void validateProductId(int billId, int productId) throws ValidationException {

    validateBillId(billId);
    Bill bill = billService.getBillById(billId);
    Integer quantity = bill.getQuantity(productId);
    if(quantity == null)
      throw new ValidationException("Enter a valid product Id");
  }
  /**
   * Validates the quantity to be removed
   *
   * @param billId id of the bill
   * @param productId id of the product
   * @param productQuantity quantity to be removed
   * @throws ValidationException when quantity is invalid
   */
  @Override
  public void validateProductQuantityToBeRemoved(int billId, int productId, int productQuantity)
      throws ValidationException {

        if(productQuantity <= 0)
            throw new ValidationException("Enter a valid quantity");
        validateBillId(billId);
        validateProductId(billId,productId);
        Bill bill = billService.getBillById(billId);
        if(bill.getQuantity(productId) < productQuantity)
            throw new ValidationException("Enter a valid quantity to be removed");
    }
}
