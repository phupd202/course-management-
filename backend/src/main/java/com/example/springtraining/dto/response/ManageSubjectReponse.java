package com.example.springtraining.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ManageSubjectReponse {
    private Long subjectId;

    private String nameSubject;

    private Integer estimate;

    private String content;

    private Integer sumSubject;

    private Integer sumEstimate;
}
