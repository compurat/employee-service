package com.human_interference.employee;

import com.human_interference.employee.data.Department;
import com.human_interference.employee.data.Employee;
import com.human_interference.employee.data.EmployementStatus;
import com.human_interference.employee.models.DepartmentModel;
import com.human_interference.employee.models.EmployeeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;


public class EmployeeMapperTest {
    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    @Test
    public void testEmployeeToEmployeeModel() {
        Employee employee = createEmployee();
        employeeMapper.employeeToEmployeeModel(employee);
        Assertions.assertTrue(areEqual(employee, employeeMapper.employeeToEmployeeModel(employee)));
    }

    @Test
    public void testEmployeeModelToEmployee() {
        EmployeeModel employeeModel = createEmployeeModel();
        employeeMapper.employeeModelToEmployee(employeeModel);
        Assertions.assertTrue(areEqual(employeeMapper.employeeModelToEmployee(employeeModel), employeeModel));
    }


    @Test
    public void testUpdateEmployeeFromUpdateModel() {
        Employee employee = createEmployee();
        EmployeeModel employeeModel = createEmployeeModel();
        employeeMapper.updateEmployeeFromUpdateModel(employee, employeeModel);
        Assertions.assertTrue(areEqual(employee, employeeModel));
    }

    public static Employee createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setName("Doe");
        employee.setEmail("test@test.com");
        employee.setPhoneNumber("1234567890");
        employee.setAddress("123 Main St");
        employee.setSalary(50000.0F);
        employee.setDepartment(createDepartment());
        employee.setStartDate(LocalDate.parse("2023-01-01"));
        employee.setEndDate(LocalDate.parse("2023-12-31"));
        employee.setStatus(EmployementStatus.ACTIVE);
        return employee;
    }

    public static EmployeeModel createEmployeeModel() {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setFirstName("John");
        employeeModel.setName("Doe");
        employeeModel.setEmail("test@test.com");
        employeeModel.setPhoneNumber("1234567890");
        employeeModel.setAddress("123 Main St");
        employeeModel.setSalary(50000.0F);
        employeeModel.setDepartment(createDepartmentModel());
        employeeModel.setStartDate(LocalDate.parse("2023-01-01"));
        employeeModel.setEndDate(LocalDate.parse("2023-12-31"));
        employeeModel.setStatus(EmployementStatus.ACTIVE);
        return employeeModel;
    }

    static private DepartmentModel createDepartmentModel() {
        DepartmentModel departmentModel = new DepartmentModel();
        departmentModel.setName("IT");
        departmentModel.setPhoneNumber("0987654321");
        departmentModel.setDescription("Information Technology");
        return departmentModel;
    }

    static private Department createDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");
        department.setPhoneNumber("0987654321");
        department.setDescription("Information Technology");
        return department;
    }

    static boolean areEqual(List<Employee> employees, List<EmployeeModel> employeeModels) {
        for (int i = 0; i < employees.size(); i++) {
            if (!areEqual(employees.get(i), employeeModels.get(i))) {
                return false;
            }
        }
        return true;
    }

    static boolean areEqual(Employee employee, EmployeeModel employeeModel) {
        return employee.getName().equals(employeeModel.getName()) &&
                employee.getFirstName().equals(employeeModel.getFirstName()) &&
                employee.getEmail().equals(employeeModel.getEmail()) &&
                employee.getPhoneNumber().equals(employeeModel.getPhoneNumber()) &&
                employee.getAddress().equals(employeeModel.getAddress()) &&
                employee.getSalary().equals(employeeModel.getSalary()) &&
                employee.getStartDate().equals(employeeModel.getStartDate()) &&
                employee.getEndDate().equals(employeeModel.getEndDate()) &&
                employee.getStatus().equals(employeeModel.getStatus()) &&
                employee.getDepartment().getName().equals(employeeModel.getDepartment().getName()) &&
                employee.getDepartment().getPhoneNumber().equals(employeeModel.getDepartment().getPhoneNumber()) &&
                employee.getDepartment().getDescription().equals(employeeModel.getDepartment().getDescription());
    }
}