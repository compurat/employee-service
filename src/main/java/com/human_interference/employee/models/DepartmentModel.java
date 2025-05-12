package com.human_interference.employee.models;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class DepartmentModel {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\+?[0-9]{2}-?[0-9]{8}", message = "Phone number must be valid")
    private String PhoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
