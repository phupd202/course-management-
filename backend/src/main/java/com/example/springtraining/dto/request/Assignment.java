package com.example.springtraining.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Assignment {
    private String beginDate;

    private String endDate;

    private String beginTime;

    private String endTime;

    private Integer currentHours;
}
