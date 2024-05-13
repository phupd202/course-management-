package com.example.springtraining.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springtraining.dto.lecturer.PersonaleEventDto;
import com.example.springtraining.dto.lecturer.ResponsePersonalEvent;
import com.example.springtraining.entity.Lecturer;
import com.example.springtraining.entity.PersonalEvent;
import com.example.springtraining.repository.LecturerRepository;
import com.example.springtraining.repository.PersonalEventRepository;
import com.example.springtraining.service.PersonalEventService;

@Service
public class PersonalEventServiceImpl implements PersonalEventService{

    private final PersonalEventRepository personalEventRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private LecturerRepository lecturerRepository;

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public PersonalEventServiceImpl(PersonalEventRepository personalEventRepository) {
        this.personalEventRepository = personalEventRepository;
    }

    @Override
    public void saveEvent(PersonaleEventDto personaleEventDto, String emailLecturer) {
        if(personaleEventDto == null) {
            throw new NullPointerException("personalEventDto is null");
        } else {
            PersonalEvent personalEvent = new PersonalEvent();
            
            // set data
            personalEvent.setTitle(personaleEventDto.getTitle());

            String dateStr = personaleEventDto.getDate();
            personalEvent.setDate(LocalDate.parse(dateStr, dateFormatter));

            String startTimeStr = personaleEventDto.getBeginTime();
            personalEvent.setBeginTime(LocalTime.parse(startTimeStr));

            String endTimeStr = personaleEventDto.getEndTime();
            personalEvent.setEndTime(LocalTime.parse(endTimeStr));

            personalEvent.setTakenote(personaleEventDto.getTakenote());

            Lecturer lecturer = lecturerRepository.findLecturerByEmail(emailLecturer);
            personalEvent.setLecturer(lecturer);

            personalEventRepository.save(personalEvent);
            lecturerRepository.save(lecturer);
        }
    }

    @Override
    public List<ResponsePersonalEvent> getPersonalEvent(String email) {
        List<PersonalEvent> personalEvents = personalEventRepository.findPersonalEventOfLecturer(email);

        return personalEvents.stream()
                            .map(personalEvent -> convertToResponsePersonalEvent(personalEvent))
                            .toList();
    }

    public ResponsePersonalEvent convertToResponsePersonalEvent(PersonalEvent personalEvent) {
        LocalTime beginTime = personalEvent.getBeginTime();
        LocalTime endTime = personalEvent.getEndTime();
        LocalDate date = personalEvent.getDate();

        String beginTimeStr = beginTime.format(timeFormatter);
        String endTimeStr = endTime.format(timeFormatter);

        String dateStr = date.format(dateFormatter);

        String start = dateStr + " " + beginTimeStr;
        String end = dateStr + " " + endTimeStr;

        ResponsePersonalEvent responsePersonalEvent = new ResponsePersonalEvent();
        responsePersonalEvent.setTitle(personalEvent.getTitle());
        responsePersonalEvent.setStart(start);
        responsePersonalEvent.setEnd(end);
        responsePersonalEvent.setTakenote(personalEvent.getTakenote());
        responsePersonalEvent.setColor(personalEvent.getColor());
        return responsePersonalEvent;
    }
}
