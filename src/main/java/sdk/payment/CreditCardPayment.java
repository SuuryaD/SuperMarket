package sdk.payment;

import sdk.bill.service.BillServiceImpl;
import sdk.util.ValidationException;

public class CreditCardPayment implements CardPayment {

  private String cardNumber;
  private String cardName;
  private String cardExpiry;
  private int billId;

  public CreditCardPayment(String cardNumber, String cardName, String cardExpiry, int billId) {
    this.cardNumber = cardNumber;
    this.cardName = cardName;
    this.cardExpiry = cardExpiry;
    this.billId = billId;
  }

  @Override
  public boolean payWithCard() throws ValidationException {
    if (new BillServiceImpl().isPaid(billId))
      throw new ValidationException("The bill is already paid.");

    if (!cardNumber.matches("(^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$)"))
      throw new ValidationException("Enter a valid card number");
    if (!cardName.matches("^[A-Za-z\\s]+[A-Za-z\\s]*$"))
      throw new ValidationException("Enter a valid name");
    if (!cardExpiry.matches("(^(0[1-9]|1[012])-[0-9]{2})"))
      throw new ValidationException("Enter a valid Expiry date");
    return new PaymentService().payWithCard(cardNumber, cardName, cardExpiry, billId);
  }

  @Override
  public boolean pay() throws ValidationException {
    return payWithCard();
  }
}
