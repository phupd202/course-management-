package com.example.springtraining.dto.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MailConfirmRegister {
    private String name;

    private String phone;

    private String address;

    private String email;

    private Long courseId;

    private Long classroomId;
}
