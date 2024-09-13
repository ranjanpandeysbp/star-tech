package com.ims.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String nationality;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dob;
    private String gender;
    private String password;
    private String kvkNumber;
}
