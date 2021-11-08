package sdk.employee.repository;

import org.mindrot.jbcrypt.BCrypt;
import sdk.employee.domain.EmployeeLogin;

import java.util.ArrayList;
import java.util.List;

public class EmployeeLoginRepositoryImpl implements EmployeeLoginRepository {

  private static EmployeeLoginRepositoryImpl instance;

  public static EmployeeLoginRepositoryImpl getInstance() {
    if (instance == null)
      instance = new EmployeeLoginRepositoryImpl();
    return instance;
  }

  private final List<EmployeeLogin> loginList;

  public EmployeeLoginRepositoryImpl() {
    loginList = new ArrayList<>();
  }

  @Override
  public int authenticate(String username, String password) {
    for (EmployeeLogin emp : loginList) {
      if (emp.getEmployeeUsername().equals(username)
          && BCrypt.checkpw(password, emp.getEmployeePassword())) return emp.getEmployeeId();
    }
    return 0;
  }

  @Override
  public boolean addEmployee(int id, String username, String pass) {
    return loginList.add(new EmployeeLogin(id, username, pass));
  }
}
