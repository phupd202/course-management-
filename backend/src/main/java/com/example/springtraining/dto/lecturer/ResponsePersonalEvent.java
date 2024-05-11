package com.example.springtraining.dto.lecturer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponsePersonalEvent {
    private String title;
    private String start;
    private String end;
    private String takenote;
}
