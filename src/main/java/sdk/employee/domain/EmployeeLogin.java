package sdk.employee.domain;

public class EmployeeLogin {

    private final int employeeId;
    private final String employeeUsername;
    private final String employeePassword;

    public EmployeeLogin(int employeeId, String employeeUsername, String employeePassword) {
        this.employeeId = employeeId;
        this.employeeUsername = employeeUsername;
        this.employeePassword = employeePassword;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }
}
