package com.example.springtraining.mapper;

import com.example.springtraining.dto.LecturerDto;
import com.example.springtraining.entity.Lecturer;

public class LecturerDtoMapper {
    public static LecturerDto mapToDto(Lecturer lecturer) {
        if(lecturer == null) {
            throw new NullPointerException("Lecturer null");
        }

        LecturerDto lecturerDto = new LecturerDto();
        lecturerDto.setLecturerId(lecturer.getLecturerId());
        lecturerDto.setNameLecturer(lecturer.getNameLecture());
        return lecturerDto;
    }
}
