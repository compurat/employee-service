package com.human_interference.employee.models;

import com.human_interference.employee.data.EmployementStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class EmployeeModel {
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Email is mandatory")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Email address must be valid"
    )
    private String email;
    @Pattern(regexp = "\\+?[0-9]{2}-?[0-9]{8}", message = "Phone number must be valid")
    private String phoneNumber;
    @NotBlank(message = "Address is mandatory")
    private String address;
    @NotBlank(message = "Salary is mandatory")
    private Float salary;
    private EmployementStatus status;
    @NotBlank(message = "Startdate is mandatory")
    private LocalDate startDate;
    @NotBlank(message = "Enddate is mandatory")
    private LocalDate endDate;
    @NotBlank(message = "Department is mandatory")
    private DepartmentModel department;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public EmployementStatus getStatus() {
        return status;
    }

    public void setStatus(EmployementStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }
}
