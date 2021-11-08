package sdk.employee.controller;

import sdk.util.ValidationException;

public interface EmployeeValidation {
    void validateEmployeeName(String employeeName) throws ValidationException;

    void validateEmployeeUsername(String username) throws ValidationException;

    void validatePassword(String pass) throws ValidationException;

    void validateEmployeeId(int employeeId) throws ValidationException;
}
