package com.human_interference;

import com.human_interference.employee.EmployeeController;
import com.human_interference.employee.data.Department;
import com.human_interference.employee.data.DepartmentRepository;
import com.human_interference.employee.data.Employee;
import com.human_interference.employee.data.EmployeeRepository;
import com.human_interference.employee.data.EmployementStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest
public class EmployeeServiceApplicationTests {
    private MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeController employeeController;
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("employee")
            .withUsername("test_user")
            .withPassword("test_password");

    @BeforeAll
    public static void startContainer() {

    }

    @BeforeEach
    public void init() {
        MockMvcBuilder mockMvcBuilder = MockMvcBuilders.standaloneSetup(employeeController);
        this.mockMvc = mockMvcBuilder.build();
        // Database leegmaken en vullen met testdata via Repository (geen SQL scripts nodig)
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();

        Department it = new Department();
        it.setName("IT");
        it.setDescription("Information Technology");
        it.setPhoneNumber("1234567890");
        departmentRepository.save(it);

        Employee employee = new Employee();
        employee.setFirstName("Pete");
        employee.setName("Parker");
        employee.setEmail("pete.parker@test.com");
        employee.setPhoneNumber("9876543210");
        employee.setAddress("456 Elm St");
        employee.setSalary(60000.0f);
        employee.setStatus(EmployementStatus.ACTIVE);
        employee.setStartDate(LocalDate.of(2023, 1, 1));
        employee.setEndDate(LocalDate.of(2023, 12, 31));
        employee.setDepartment(it);
        employeeRepository.save(employee);
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void contextLoads() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Test
    void testAddEmployee() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.ALL));
        headers.setConnection(List.of("keep-alive"));
        mockMvc.perform(post("/api/v1/employee/add")
                        .headers(headers)
                        .content("{\"firstName\":\"John\",\"name\":\"Doe\",\"email\":\"test@test.com\"," +
                                "\"phoneNumber\":\"1234567890\",\"address\":\"123 Main St\",\"salary\":50000.0," +
                                "\"status\":\"ACTIVE\",\"startDate\":[2023,1,1],\"endDate\":[2023,12,31]," +
                                "\"department\":{\"name\":\"FIN\",\"description\":\"finance\"," +
                                "\"employees\":null,\"phoneNumber\":\"2345678999\"}}"))
                .andExpect(status().isOk());

    }

    @Test
    void selectEmployeeByEmail() throws Exception {
        mockMvc.perform(get("/api/v1/employee/find/email/pete.parker@test.com")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateEmployee() throws Exception {
        mockMvc.perform(put("/api/v1/employee/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"firstName\":\"Pete\"," +
                                "\"name\":\"Parker\"," +
                                "\"email\":\"pete.parker@test.com\"," +
                                "\"phoneNumber\":\"9876543210\"," +
                                "\"address\":\"456 Elm St\"," +
                                "\"salary\":60000.0," +
                                "\"status\":\"ACTIVE\"," +
                                "\"startDate\":\"2023-01-01\"," +
                                "\"endDate\":\"2023-12-31\"," +
                                "\"department\":{" +
                                "\"id\":1," +
                                "\"name\":\"IT\"," +
                                "\"description\":\"Information Technology\"," +
                                "\"phoneNumber\":\"1234567890\"}}"))
                .andExpect(status().isOk());
    }

    @Test
    void selectEmployeeByNameParts() throws Exception {
        mockMvc.perform(get("/api/v1/employee/find/name/park")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void selectEmployeeBySalaryRange() throws Exception {
        mockMvc.perform(get("/api/v1/employee/find/salary/30000/60000")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void selectEmployeeByDepartment() throws Exception {
        mockMvc.perform(get("/api/v1/employee/find/department/FIN")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/v1/employee/delete/pete.parker@test.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @AfterAll
    public static void closeContainer() {
        postgreSQLContainer.stop();
    }
}
