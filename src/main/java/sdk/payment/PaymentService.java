package sdk.payment;

import sdk.bill.service.BillServiceImpl;
import sdk.util.ValidationException;

public class PaymentService {
  private BillServiceImpl billService;

  public PaymentService() {
    this.billService = new BillServiceImpl();
  }

  public boolean payWithCard(String cardNumber, String cardName, String cardExpiry, int billId)
      throws ValidationException {
    billService.confirmBillPayment(billId);
    return true;
  }

  public boolean payWithUpi(String upiId, int billId) throws ValidationException {
    billService.confirmBillPayment(billId);
    return true;
  }
}
