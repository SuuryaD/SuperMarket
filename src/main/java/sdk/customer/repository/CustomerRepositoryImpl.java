package sdk.customer.repository;

import sdk.customer.Customer;

import java.util.ArrayList;
import java.util.List;

/** static class contains the list of customers. */
public class CustomerRepositoryImpl implements CustomerRepository {

  private static CustomerRepositoryImpl instance;
  private final List<Customer> customerList;

  public static CustomerRepositoryImpl getInstance() {
    if (instance == null) instance = new CustomerRepositoryImpl();
    return instance;
  }

  public CustomerRepositoryImpl() {
    customerList = new ArrayList<>();
    initialize();
  }

  @Override
  public List<Customer> getAllCustomers() {
    return customerList;
  }

  /**
   * adds Customer to the customer list
   *
   * @param customer customer to be added
   */
  @Override
  public void addCustomer(Customer customer) {
    customerList.add(customer);
  }

  @Override
  public Customer getCustomerById(int customerId) {

    for (Customer customer : customerList) {
      if (customer.getId() == customerId) return customer;
    }
    return null;
  }

  private void initialize() {
    customerList.add(new Customer("surya", "address1"));
    customerList.add(new Customer("dhanush", "address2"));
  }
}
