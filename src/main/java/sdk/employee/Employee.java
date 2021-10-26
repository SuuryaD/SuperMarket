package sdk.employee;

import java.util.List;

/** Contains all the details of an employee. */
public abstract class Employee {

  private static int NUMBER = 1;
  private final int id;
  private final String name;
  private final String username;
  private final String Password;

  public Employee(String name, String username, String password) {
    this.id = NUMBER;
    NUMBER++;
    this.name = name;
    this.username = username;
    this.Password = password;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return Password;
  }

  public abstract void redirect(Visitor visitor);

  public abstract String getRole();

}
