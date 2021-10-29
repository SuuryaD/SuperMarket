package sdk.customer.service;

import sdk.bill.BillDetails;
import sdk.bill.service.BillService;
import sdk.customer.Customer;
import sdk.customer.repository.CustomerRepository;
import sdk.util.Factory;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final BillService billService;

  public CustomerServiceImpl() {
    customerRepository = Factory.createCustomerRepository();
    billService = Factory.createBillService();
  }

  @Override
  public boolean checkCustomerId(int customerId) {
    Customer customer = customerRepository.getCustomerById(customerId);
    return customer != null;
  }

  @Override
  public int addCustomer(String customerName, String customerAddress) {
    Customer customer = new Customer(customerName, customerAddress);
    customerRepository.addCustomer(customer);
    return customer.getId();
  }

  public Double getTotalPurchaseOfCustomer(int customerId){
    List<BillDetails> ls = billService.getCustomerBills(customerId);
    Double amount = 0.0;
    for(BillDetails bill : ls){
      amount += bill.getAmount();
    }
    return amount;
  }

  public int getNoOfBillsOfCustomer(int customerId){
    List<BillDetails> ls = billService.getCustomerBills(customerId);
    return ls.size();
  }

  public List<BillDetails> getBillsOfCustomer(int customerId){
    return billService.getCustomerBills(customerId);
  }

  @Override
  public List<Customer> getAllCustomers() {
    List<Customer> ls = customerRepository.getAllCustomers();
    return ls;
  }
}
