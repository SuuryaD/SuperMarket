package sdk.payment;

import sdk.bill.service.BillServiceImpl;
import sdk.util.ValidationException;

public class UpiPaymentImpl implements UpiPayment {

  private String upiId;
  private int billId;

  public UpiPaymentImpl(String upiId, int billId) {
    this.upiId = upiId;
    this.billId = billId;
  }

  @Override
  public boolean payWithUpi() throws ValidationException {

    if (new BillServiceImpl().isPaid(billId))
      throw new ValidationException("The bill is already paid.");

    if (!upiId.matches("^([_a-zA-Z0-9-]+(\\\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+)$"))
      throw new ValidationException("Enter a valid UPI id.");
    if (billId <= 0) throw new ValidationException("Enter a valid Bill Id");
    return new PaymentService().payWithUpi(upiId, billId);
  }

  @Override
  public boolean pay() throws ValidationException {
    return payWithUpi();
  }
}
