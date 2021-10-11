package customer;

import java.util.ArrayList;
import java.util.List;

/**
 * static class contains the list of customers.
 */
public class CustomerList {

    private static final List<Customer> customerList = new ArrayList<>();

    public static List<Customer> getCustomerList(){
        return customerList;
    }

    /**
     * adds Customer to the customer list
     * @param customer customer to be added
     */
    public static void addCustomer(Customer customer){
        customerList.add(customer);
    }

    public static Customer getCustomerById(int id){

        for(Customer customer : customerList){
            if(customer.getId() == id)
                return customer;
        }
        return null;

    }

}
