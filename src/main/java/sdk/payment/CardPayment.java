package sdk.payment;

import sdk.util.ValidationException;

public interface CardPayment extends Payment {

  boolean payWithCard() throws ValidationException;
}
