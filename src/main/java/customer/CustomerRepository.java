package customer;

import java.util.ArrayList;
import java.util.List;

/** static class contains the list of customers. */
public class CustomerRepository {

  private static CustomerRepository instance;
  private final List<Customer> customerList;

  public static CustomerRepository getInstance() {
    if (instance == null) {
      instance = new CustomerRepository();
    }
    return instance;
  }

  public CustomerRepository() {
    customerList = new ArrayList<>();
    initialize();
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

  private void initialize(){
    customerList.add(new Customer("surya", "address1"));
    customerList.add(new Customer("dhanush", "address2"));
  }
}
