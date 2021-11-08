package sdk.customer.controller;

import sdk.bill.BillDetails;
import sdk.customer.Customer;
import sdk.customer.service.CustomerService;
import sdk.employee.domain.Employee;
import sdk.util.Factory;
import sdk.util.ValidationException;

import java.util.List;

/**
 * customer controller implementation
 */
public class CustomerControllerImpl implements CustomerController {

  private final CustomerService customerService;
  private final CustomerValidation customerValidation;
  private final Employee emp;

  public CustomerControllerImpl(Employee emp) {
    customerService = Factory.createCustomerService();
    customerValidation = Factory.createCustomerValidation();
    this.emp = emp;
  }

  @Override
  public boolean checkCustomerId(int customerId) throws ValidationException {
    customerValidation.validateCustomerId(customerId);
    return customerService.checkCustomerId(customerId);
  }

  /**
   * Adds a new customer
   * @param name name of the customer
   * @param address address of the customer
   * @return id of the new customer
   * @throws ValidationException when input is invalid
   */
  @Override
  public int addNewCustomer(String name, String address) throws ValidationException {
    customerValidation.validateCustomerName(name);
    customerValidation.validateCustomerAddress(address);
    return customerService.addCustomer(name, address);
  }

  /**
   * Gets all the of the bills of customer
   * @param customerId id of the customer
   * @return list of bills of customer
   * @throws ValidationException when input is invalid
   */
  @Override
  public List<BillDetails> getBillsOfCustomer(int customerId) throws ValidationException {
    customerValidation.validateCustomerId(customerId);
    return customerService.getBillsOfCustomer(customerId);
  }

  @Override
  public List<Customer> getAllCustomers() {
    return customerService.getAllCustomers();
  }

  /**
   * Calculates the total purchase of customer
   * @param customerId id of the customer
   * @return total purchase amount
   * @throws ValidationException when input is invalid.
   */
  @Override
  public Double getTotalPurchaseOfCustomer(int customerId) throws ValidationException {
    customerValidation.validateCustomerId(customerId);
    return customerService.getTotalPurchaseOfCustomer(customerId);
  }

  /**
   * @param customerId id of the customer
   * @return No of bills of customer
   * @throws ValidationException
   */
  @Override
  public int getNoOfBillsOfCustomer(int customerId) throws ValidationException {
    customerValidation.validateCustomerId(customerId);
    return customerService.getNoOfBillsOfCustomer(customerId);
  }
}
