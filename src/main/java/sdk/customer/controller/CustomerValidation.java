package sdk.customer.controller;

import sdk.util.ValidationException;

public interface CustomerValidation {

    void validateCustomerId(int customerId) throws ValidationException;

    void validateCustomerAddress(String address) throws ValidationException;

    void validateCustomerName(String customerName) throws ValidationException;
}
