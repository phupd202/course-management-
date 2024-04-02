package com.example.springtraining.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterestPartyDtoResponse {
    private Long courseId; 

    private String nameCourse;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phone; 

    @NotNull
    private String address;
}
