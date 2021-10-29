package sdk.payment;

import sdk.util.ValidationException;

public interface Payment {

  boolean pay() throws ValidationException;
}
