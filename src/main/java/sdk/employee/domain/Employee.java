package sdk.employee.domain;

import sdk.employee.Visitor;

/** Contains all the details of an employee. */
public abstract class Employee {

  private static int NUMBER = 1;
  private final int id;
  private final String name;
  private final String username;

  public Employee(String name, String username){
    this.id = NUMBER;
    NUMBER++;
    this.name = name;
    this.username = username;
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

  public abstract void redirect(Visitor visitor);

  public abstract String getRole();
}
