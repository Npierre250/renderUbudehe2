package com.example.demo.Domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Ubudehe {

    @GeneratedValue
    @Id
    private Long id;
    @NotBlank(message = "First Name is required!")
    private String firstName;
    @NotBlank(message = "Last Name is required!")
    private String lastName;
    @NotBlank(message = "Address is required!")
    private String address;
    @NotBlank(message = "Email is required!")
    private String email;
    @NotBlank(message = "Martial Status is required!")
    private String martialStatus;
    @NotBlank(message = "Gender is mandatory")
    private String gender;
    @NotBlank(message = "Phone is mandatory")
    private String phone;
    @NotBlank(message = "Category is mandatory")
    private String category;
    private String dob;


}
