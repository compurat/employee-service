package com.human_interference.employee;

import com.human_interference.employee.data.DepartmentRepository;
import com.human_interference.employee.data.Employee;
import com.human_interference.employee.data.EmployeeRepository;
import com.human_interference.employee.data.EmployementStatus;
import com.human_interference.employee.models.EmployeeModel;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.human_interference.employee.EmployeeMapperTest.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @Mock
    DepartmentRepository departmentRepository;
    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testAddEmployeeHappyFlow() {
        when(employeeRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        EmployeeModel employeeModel = createEmployeeModel();
        Employee employee = createEmployee();
        when(employeeMapper.employeeModelToEmployee(employeeModel)).thenReturn(employee);
        employeeService.addEmployee(employeeModel);
        verify(employeeRepository).save(employee);
    }

    @Test
    void testAddEmployeeAlreadyExist() {
        when(employeeRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> employeeService.addEmployee(createEmployeeModel()));
    }

    @Test
    void testUpdateEmployeeHappyFlow() {
        EmployeeModel employeeModel = createEmployeeModel();
        Employee employee = createEmployee();
        when(employeeRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        when(employeeRepository.findByEmail(employeeModel.getEmail())).thenReturn(employee);
        when(employeeMapper.updateEmployeeFromUpdateModel(employee,employeeModel)).thenReturn(employee);
        employeeService.updateEmployee(employeeModel);
        verify(employeeRepository).save(employee);
    }

    @Test
    void testUpdateEmployeeNotExist() {
        when(employeeRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> employeeService.updateEmployee(createEmployeeModel()));
    }

    @Test
    void testDeleteEmployeeHappyFlow() {
        EmployeeModel employeeModel = createEmployeeModel();
        Employee employee = createEmployee();
        String email = employeeModel.getEmail();
        when(employeeRepository.existsByEmail(email)).thenReturn(true);
        when(employeeRepository.findByEmail(email)).thenReturn(employee);
        employeeService.deleteEmployee(employeeModel.getEmail());
        verify(employeeRepository).save(employee);
    }

    @Test
    void testDeleteEmployeeNotExist() {
        when(employeeRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> employeeService.deleteEmployee(createEmployeeModel().getEmail()));
    }

    @Test
    void testByNameContainingHappyFlow() {
        when(employeeRepository.existsByNameContainingIgnoreCase(Mockito.anyString())).thenReturn(true);
        List<EmployeeModel> employeeModels = List.of(createEmployeeModel());
        List<Employee> employees = List.of(createEmployee());
        when(employeeMapper.employeeListToEmployeeModelList(employees)).thenReturn(employeeModels);
        when(employeeRepository.findByNameContainingIgnoreCase(Mockito.anyString())).thenReturn(employees);
        assertTrue(areEqual(employees, employeeService.findByNameContaining("")));
    }

    @Test
    void testFindByNameContainingNotExist() {
        assertThrows(IllegalArgumentException.class, () -> employeeService.getEmployeeByEmail(createEmployeeModel().getEmail()));
    }

    @Test
    void testGetEmployeeByEmailHappyFlow() {
        when(employeeRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        EmployeeModel employeeModel = createEmployeeModel();
        Employee employee = createEmployee();
        when(employeeMapper.employeeToEmployeeModel(employee)).thenReturn(employeeModel);
        when(employeeRepository.findByEmail(Mockito.anyString())).thenReturn(employee);
        assertTrue(areEqual(employee, employeeService.getEmployeeByEmail(employeeModel.getEmail())));
    }

    @Test
    void testGetEmployeeByEmailNotExist() {
        when(employeeRepository.existsByNameContainingIgnoreCase(Mockito.anyString())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> employeeService.findByNameContaining(""));
    }

    @Test
    void testGetEmployeeBySalaryRangeHappyFlow() {
        List<EmployeeModel> employeeModels = List.of(createEmployeeModel());
        List<Employee> employees = List.of(createEmployee());
        when(employeeMapper.employeeListToEmployeeModelList(employees)).thenReturn(employeeModels);
        when(employeeRepository.findBySalaryBetween(Mockito.anyFloat(), Mockito.anyFloat())).thenReturn(employees);
        assertTrue(areEqual(employees, employeeService.findBySalaryRange(Mockito.anyFloat(), Mockito.anyFloat())));
    }

    @Test
    void testGetEmployeeBySalaryRangeNotExist() {
        when(employeeRepository.findBySalaryBetween(Mockito.anyFloat(), Mockito.anyFloat())).thenReturn(List.of());
        assertThrows(IllegalArgumentException.class, () -> employeeService.findBySalaryRange(Mockito.anyFloat(), Mockito.anyFloat()));
    }

    @Test
    void testGetEmployeeByStatusHappyFlow() {
        List<EmployeeModel> employeeModels = List.of(createEmployeeModel());
        List<Employee> employees = List.of(createEmployee());
        when(employeeMapper.employeeListToEmployeeModelList(employees)).thenReturn(employeeModels);
        EmployementStatus active = EmployementStatus.ACTIVE;
        when(employeeRepository.findByStatus(active)).thenReturn(employees);
        assertTrue(areEqual(employees, employeeService.findByStatus(active)));
    }

    @Test
    void testGetEmployeeByStatusNotExist() {
        EmployementStatus inactive = EmployementStatus.INACTIVE;
        when(employeeRepository.findByStatus(inactive)).thenReturn(List.of());
        assertThrows(IllegalArgumentException.class, () -> employeeService.findByStatus(inactive));
    }

    @Test
    void testGetEmployeeByStartDayRangeHappyFlow() {
        List<EmployeeModel> employeeModels = List.of(createEmployeeModel());
        List<Employee> employees = List.of(createEmployee());
        when(employeeMapper.employeeListToEmployeeModelList(employees)).thenReturn(employeeModels);
        LocalDate startDate = LocalDate.of(2022, 11, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 30);
        when(employeeRepository.findByStartDateBetween(startDate, endDate)).thenReturn(employees);
        when(employeeRepository.findByStartDateBetween(startDate, endDate)).thenReturn(employees);
        assertTrue(areEqual(employees, employeeService.findByStartDateRange(startDate, endDate)));
    }

    @Test
    void testGetEmployeeByStartDayRangeNotExist() {
        EmployementStatus inactive = EmployementStatus.INACTIVE;
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2025, 3, 30);
        when(employeeRepository.findByStartDateBetween(startDate, endDate)).thenReturn(List.of());
        assertThrows(IllegalArgumentException.class, () -> employeeService.findByStartDateRange(startDate, endDate));
    }

}