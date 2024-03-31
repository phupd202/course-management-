package com.example.springtraining.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class AddClassroom {
    private Long courseId;

    private String codeClassroom;

    private String beginDate;

    private String endDate;
}
