package sdk.employee;

import sdk.employee.domain.*;

public interface Visitor {

  void visit(Manager manager);

  void visit(Biller biller);

  void visit(Delivery delivery);

  void visit(FloorStaff floorStaff);

  void visit(Cashier cashier);
}
