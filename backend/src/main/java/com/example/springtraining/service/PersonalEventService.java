package com.example.springtraining.service;

import java.util.List;

import com.example.springtraining.dto.lecturer.PersonaleEventDto;
import com.example.springtraining.dto.lecturer.ResponsePersonalEvent;

public interface PersonalEventService {
    void saveEvent( PersonaleEventDto personaleEventDto, String emailLecturer); 

    List<ResponsePersonalEvent> getPersonalEvent(String email);
}
