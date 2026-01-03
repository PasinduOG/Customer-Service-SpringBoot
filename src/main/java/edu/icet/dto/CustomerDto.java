package edu.icet.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "type", "name", "age", "email", "salary"})
public class CustomerDto {
    private Integer id;
    @NotEmpty(message = "Type cannot be empty!")
    private String type;

    @NotEmpty(message = "Name cannot be empty!")
    private String name;

    @NotNull(message = "Age cannot be empty")
    @Positive(message = "Age must be positive")
    private Integer age;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    private Double salary;
}
