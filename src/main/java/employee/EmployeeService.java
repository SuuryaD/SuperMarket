package employee;

import org.mindrot.jbcrypt.BCrypt;
import util.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService() {
        employeeRepository = EmployeeRepository.getInstance();
    }

    public boolean addEmployee(String name, String username, String pass, int admin) {
        if(isUsernameAvailable(username))
            return false;
        return employeeRepository.addEmployee(name, username, BCrypt.hashpw(pass, BCrypt.gensalt(10)), admin == 1);
    }

    private boolean isUsernameAvailable(String username){
        Employee emp = employeeRepository.getEmployeeByUsername(username);
        return emp != null;
    }

    private boolean isEmployeeAvailable(int id){
        Employee emp = employeeRepository.getEmployeeById(id);
        return emp != null;
    }
    public boolean removeEmployee(int id) {
        if(!isEmployeeAvailable(id))
            return false;
        return employeeRepository.removeEmployee(id);
    }

    public int authenticate(String username, String pass) {
        Employee emp = employeeRepository.getEmployeeByUsername(username);
        if(emp == null)
            return 0;
        if(BCrypt.checkpw(pass, emp.getPassword())){
            if(emp.isAdmin())
                return 1;
            return 2;
        }

        return 0;
    }

    public String displayAllEmployees() {
        List<Employee> ls = employeeRepository.getAllEmployees();

        ArrayList<String> headers = new ArrayList<>();
        headers.add("Employee Id");
        headers.add("Employee Name");
        headers.add("Role");

        ArrayList<ArrayList<String>> content = new ArrayList<>();
        for (Employee emp : ls) {

            content.add(
                    new ArrayList<>(
                            Arrays.asList(
                                    String.valueOf(emp.getId()),
                                    emp.getName(),
                                    emp.isAdmin() ? "ADMIN" : "CASHIER")));
        }
        return Globals.printTable(headers, content);
    }
}
