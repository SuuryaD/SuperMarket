package customer;

import java.util.ArrayList;
import java.util.List;

/** static class contains the list of customers. */
public class CustomerList {

  private static CustomerList instance;
  private final List<Customer> customerList = new ArrayList<>();

  public static CustomerList getInstance() {
    if (instance == null) {
      instance = new CustomerList();
    }
    return instance;
  }

  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * adds Customer to the customer list
   *
   * @param customer customer to be added
   */
  public void addCustomer(Customer customer) {
    customerList.add(customer);
  }

  public Customer getCustomerById(int id) {

    for (Customer customer : customerList) {
      if (customer.getId() == id) return customer;
    }
    return null;
  }
}
