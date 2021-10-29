package sdk.payment;

import sdk.util.ValidationException;

public interface UpiPayment extends Payment {

  boolean payWithUpi() throws ValidationException;
}
