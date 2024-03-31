package com.example.springtraining.dto.guest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InterestedPartyDto {
    private Long courseId; 

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
