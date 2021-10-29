package sdk.employee.repository;

import org.mindrot.jbcrypt.BCrypt;
import sdk.employee.domain.Employee;
import sdk.employee.domain.EmployeeLogin;

import java.util.ArrayList;
import java.util.List;

public class EmployeeLoginRepository {

    private static EmployeeLoginRepository instance;

    public static EmployeeLoginRepository getInstance(){
        if(instance == null)
            instance = new EmployeeLoginRepository();
        return instance;
    }

    private final List<EmployeeLogin> loginList;
    public EmployeeLoginRepository(){
        loginList = new ArrayList<>();
    }

    public int authenticate(String username, String password){
        for(EmployeeLogin emp : loginList){
            if(emp.getEmployeeUsername().equals(username) && BCrypt.checkpw(password, emp.getEmployeePassword()))
                return emp.getEmployeeId();
        }
        return 0;
    }

    public boolean addEmployee(int id, String username, String pass) {
        return loginList.add(new EmployeeLogin(id, username, pass));
    }
}
