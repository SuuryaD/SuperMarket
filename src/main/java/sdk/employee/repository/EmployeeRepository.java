package sdk.employee.repository;

import sdk.employee.domain.Employee;

import java.util.List;

public interface EmployeeRepository {
  Boolean addManagerEmployee(String employeeName, String username, String pass);

  Boolean  addCashierEmployee(String employeeName, String username, String pass);

  Boolean  addFloorStaffEmployee(String employeeName, String username, String hashpw);

  Boolean  addBillerEmployee(String employeeName, String username, String hashpw);

  Boolean  addDeliveryEmployee(String employeeName, String username, String hashpw);

  boolean removeEmployee(int employeeId);

  Employee getEmployeeByUsername(String username);

//  Employee authenticate(String username, String pass);

  Employee getEmployeeById(int employeeId);

  List<Employee> getAllEmployees();
}
