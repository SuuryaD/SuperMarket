package sdk.employee.domain;

import sdk.employee.Visitor;

public class Manager extends Employee {
  public Manager(String name, String username) {
    super(name, username);
  }

  @Override
  public void redirect(Visitor visitor) {
    visitor.visit(this);
  }

  @Override
  public String getRole() {
    return this.getClass().getSimpleName();
  }
}
