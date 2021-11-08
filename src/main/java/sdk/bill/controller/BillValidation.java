package sdk.bill.controller;

import sdk.util.ValidationException;

/** Contains all bill validation functions */
public interface BillValidation {
  void validateBillId(int billId) throws ValidationException;

  void validateProductId(int billId, int productId) throws ValidationException;

  void validateProductQuantityToBeRemoved(int billId, int productId, int productQuantity)
      throws ValidationException;
}
