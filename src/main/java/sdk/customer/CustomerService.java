package sdk.customer;

import sdk.bill.BillService;
import sdk.util.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerService {

  private final CustomerRepository customerRepository;
  private final BillService billservice;

  public CustomerService() {
    customerRepository = CustomerRepository.getInstance();
    billservice = new BillService();
  }

  public boolean checkCustomerId(int customerId) {
    Customer customer = customerRepository.getCustomerById(customerId);
    return customer != null;
  }

  public int addCustomer(String customerName, String customerAddress) {
    Customer customer = new Customer(customerName, customerAddress);
    customerRepository.addCustomer(customer);
    return customer.getId();
  }

  public String displayCustomers() {
    List<Customer> ls = customerRepository.getCustomerList();
    ArrayList<String> headers = new ArrayList<>();
    ArrayList<ArrayList<String>> content = new ArrayList<>();
    headers.add("S.No");
    headers.add("Customer id");
    headers.add("Customer name");
    headers.add("Customer address");
    headers.add("No of purchases");
    headers.add("Total Purchase amount");
    int no = 1;
    for (Customer customer : ls) {
      content.add(
          new ArrayList<>(
              Arrays.asList(
                  String.valueOf(no),
                  String.valueOf(customer.getId()),
                  customer.getName(),
                  customer.getAddress(),
                  String.valueOf(billservice.getNoOfBills(customer.getId())),
                  String.valueOf(billservice.getTotalPurchaseOfCustomer(customer.getId())))));
      no++;
    }

    return Globals.printTable(headers, content);
  }
}
