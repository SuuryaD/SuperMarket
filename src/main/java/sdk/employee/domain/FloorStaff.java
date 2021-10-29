package sdk.employee.domain;

import sdk.employee.Visitor;
import sdk.employee.domain.Employee;

public class FloorStaff extends Employee {

  public FloorStaff(String name, String username) {
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
