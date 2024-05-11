package com.example.springtraining.dto.lecturer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonaleEventDto {
    private String title;
    private String date;
    private String beginTime;
    private String endTime;
    private String takenote;
}
