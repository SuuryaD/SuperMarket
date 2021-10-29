package sdk.customer.controller;

import sdk.bill.BillDetails;
import sdk.bill.service.BillServiceImpl;
import sdk.customer.Customer;
import sdk.customer.service.CustomerService;
import sdk.employee.controller.EmployeeController;
import sdk.employee.domain.Employee;
import sdk.util.Factory;
import sdk.util.ValidationException;

import java.util.List;

public class CustomerController {

  private final CustomerService customerService;
  private final Employee emp;

  public CustomerController(Employee emp) {
    customerService = Factory.createCustomerService();
    this.emp = emp;
  }

  public boolean checkCustomerId(int customerId) throws ValidationException {

    if (customerId <= 0) throw new ValidationException("Invalid id");

    return customerService.checkCustomerId(customerId);
  }

  public int addNewCustomer(String name, String address) throws ValidationException {
    if (name.length() == 0 && !name.matches("^[a-zA-Z]+( [a-zA-Z]+)?$"))
      throw new ValidationException("Enter a valid name");

    if (address.length() == 0) throw new ValidationException("Enter a valid address");
    return customerService.addCustomer(name, address);
  }

  public List<BillDetails> getBillsOfCustomer(int customerId){
    return customerService.getBillsOfCustomer(customerId);
  }


  public List<Customer> getAllCustomers() {
    return customerService.getAllCustomers();
  }

  public Double getTotalPurchaseOfCustomer(int customerId) {
    return customerService.getTotalPurchaseOfCustomer(customerId);
  }

  public int getNoOfBillsOfCustomer(int customerId) {
    return customerService.getNoOfBillsOfCustomer(customerId);
  }
}
