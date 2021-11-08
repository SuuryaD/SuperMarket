package sdk.employee.repository;

public interface EmployeeLoginRepository {

  int authenticate(String username, String password);

  boolean addEmployee(int id, String username, String pass);
}
