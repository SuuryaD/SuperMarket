package sdk.customer;

/** Contains all the information of a customer */
public class Customer {

  private static int NUMBER = 1;
  private final int id;
  private final String name;
  private final String address;

  public String getAddress() {
    return address;
  }

  public Customer(String name, String address) {
    id = NUMBER;
    NUMBER++;
    this.name = name;
    this.address = address;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

}
