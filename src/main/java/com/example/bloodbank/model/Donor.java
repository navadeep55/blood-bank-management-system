package com.example.bloodbank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "donors")
public class Donor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 100)
    private String fullName;

    @NotBlank @Pattern(regexp = "^[0-9+() -]{7,20}$")
    private String phone;

    @Email @Size(max = 150)
    private String email;

    @NotBlank @Pattern(regexp = "^(A|B|AB|O)[+-]$")
    private String bloodGroup;

    @NotBlank @Size(max = 100)
    private String city;

    public Donor() {}
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}
