package sdk.customer.repository;

import sdk.customer.Customer;

import java.util.List;

public interface CustomerRepository {

  List<Customer> getAllCustomers();

  void addCustomer(Customer customer);

  Customer getCustomerById(int customerId);
}
