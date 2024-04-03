package com.example.springtraining.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterQueueResponse {
    private Long registerId;

    private Long courseId; 

    private Long classroomId;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phone; 

    @NotNull
    private String address;

    private Boolean createdAccount;

    private Boolean addedClassroom;
}
