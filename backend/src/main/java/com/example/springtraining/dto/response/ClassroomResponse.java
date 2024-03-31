package com.example.springtraining.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClassroomResponse {
    private Long classroomId;

    private String beginDate;

    private String endDate;

    private String codeClassroom;

    private Integer sumPeriod;

    private Integer sumStudent;
}
