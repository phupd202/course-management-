package com.example.springtraining.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class SubjectRequest {
    private String nameSubject;

    private Integer estimate;

    private List<Long> lecturersId;

    private String content;

    private String label;
}
