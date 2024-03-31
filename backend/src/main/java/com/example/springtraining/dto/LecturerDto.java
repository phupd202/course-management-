package com.example.springtraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LecturerDto {
    private Long lecturerId;

    private String nameLecturer;
}
