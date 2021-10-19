package customer;

import inventory.ValidationException;

public class CustomerController {

    private CustomerService customerService;

    public CustomerController(){
        customerService = new CustomerService();
    }

    public boolean checkCustomerId(int customerId) throws ValidationException {

        if(customerId < 0)
            throw new ValidationException("Invalid id");

        return customerService.checkCustomerId(customerId);
    }

    public int addNewCustomer(String name, String address) throws ValidationException {
        if(name.length() == 0 && !name.matches("^[a-zA-Z]+( [a-zA-Z]+)?$"))
            throw new ValidationException("Enter a valid name");

        if(address.length() == 0)
            throw new ValidationException("Enter a valid address");

        return customerService.addCustomer(name, address);

    }

    public String displayAllCustomers() {
        return customerService.displayCustomers();
    }
}
