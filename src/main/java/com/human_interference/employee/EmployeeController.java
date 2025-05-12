package com.human_interference.employee;

import com.human_interference.employee.data.EmployementStatus;
import com.human_interference.employee.models.EmployeeModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Create
    @PostMapping("/add")
    public void addEmployee(@RequestBody @Valid EmployeeModel employeeModel) {
        employeeService.addEmployee(employeeModel);
    }

    //Read
    @GetMapping("/find/email/{email}")
    public EmployeeModel getEmployeeByEmail(@PathVariable("email") String email) {
        return employeeService.getEmployeeByEmail(email);
    }

    //Update
    @PutMapping("/update")
    public void updateEmployee(@RequestBody @Valid EmployeeModel employeeModel) {
        employeeService.updateEmployee(employeeModel);
    }

    //Delete
    @DeleteMapping("/delete/{email}")
    public void deleteEmployee(@PathVariable("email") String email) {
        employeeService.deleteEmployee(email);
    }

    @GetMapping("/find/name/{name}")
    public List<EmployeeModel> findByName(@PathVariable("name") String name) {
        return employeeService.findByNameContaining(name);
    }

    @GetMapping("/find/salary/{rangeMin}/{rangeMax}")
    public List<EmployeeModel> findBySalaryRange(@PathVariable("rangeMin") Float rangeMin, @PathVariable("rangeMax") Float rangeMax) {
        return employeeService.findBySalaryRange(rangeMin, rangeMax);
    }

    @GetMapping("/find/status/{status}")
    public List<EmployeeModel> findByStatus(@PathVariable("status") String status) {
        return employeeService.findByStatus(EmployementStatus.valueOf(status.toUpperCase()));
    }

    @GetMapping("/find/department/{departmentName}")
    public List<EmployeeModel> findByDepartment(@PathVariable("departmentName") String departmentName) {
        return employeeService.findByDepartment(departmentName.toUpperCase());
    }

    @GetMapping("/find/startDate/{startDate}/{endDate}")
    public List<EmployeeModel> findByStartDate(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        return employeeService.findByStartDateRange(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }
}

