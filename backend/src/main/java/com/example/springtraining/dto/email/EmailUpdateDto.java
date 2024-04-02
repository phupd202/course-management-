package com.example.springtraining.dto.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmailUpdateDto {
    private Long mailTemplateId;

    private String nameTemplate;

    private String subject;

    private String content;

    private String createdAt;
}
