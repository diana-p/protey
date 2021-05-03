package com.test.protey.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NotNull
public class PersonDTO {

    @Size(min = 2)
    private String firstName;
    @Size(min = 3)
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
    private String phoneNumber;
    private String status;
}
