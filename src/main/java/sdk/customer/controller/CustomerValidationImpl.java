package sdk.customer.controller;

import sdk.customer.service.CustomerService;
import sdk.util.Factory;
import sdk.util.ValidationException;

public class CustomerValidationImpl implements CustomerValidation {


    private final CustomerService customerService;

    public CustomerValidationImpl() {
        customerService = Factory.createCustomerService();
    }

    /**
     * Validates the customer id
     * @param customerId id of the customer
     * @throws ValidationException when customer id is invalid
     */
    public void validateCustomerId(int customerId) throws ValidationException {
        if(!customerService.checkCustomerId(customerId))
            throw new ValidationException("Enter a valid Customer id");
    }

    /**
     * Validates customer name
     * @param customerName name of the customer
     * @throws ValidationException when name is invalid
     */
    public void validateCustomerName(String customerName) throws ValidationException {
        if (customerName.length() == 0 && !customerName.matches("^[a-zA-Z]+( [a-zA-Z]+)?$"))
            throw new ValidationException("Enter a valid customerName");
    }

    /**
     * Validates customer address
     * @param address address of the customer
     * @throws ValidationException when customer is invalid
     */
    public void validateCustomerAddress(String address) throws ValidationException {
        if (address.length() == 0)
            throw new ValidationException("Enter a valid address");
    }
}
