package com.example.springtraining.controller.admin;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.springtraining.dto.CheckAssignmentDto;
import com.example.springtraining.dto.LecturerDto;
import com.example.springtraining.dto.SubjectDto;
import com.example.springtraining.entity.Assignment;
import com.example.springtraining.entity.Lecturer;
import com.example.springtraining.entity.Subject;
import com.example.springtraining.mapper.SubjectMapper;
import com.example.springtraining.service.AssignSubjectService;
import com.example.springtraining.service.AssignmentService;
import com.example.springtraining.service.LecturerService;

@RestController
@RequestMapping("/course-management/admin/assign-classroom")
@CrossOrigin(origins = "http://localhost:8081")
public class AssignSubjectController {
    private final AssignSubjectService assignSubjectService;

    private final AssignmentService assignmentService;

    private final LecturerService lecturerService;

    public AssignSubjectController(AssignSubjectService assignSubjectService, AssignmentService assignmentService, LecturerService lecturerService) {
        this.assignSubjectService = assignSubjectService;
        this.assignmentService = assignmentService;
        this.lecturerService = lecturerService;
    } 

    @GetMapping("/{classroomId}")
    public ResponseEntity<List<SubjectDto>> getSubjecOfClass(@PathVariable(name = "classroomId") Long classroomId) {
        if(classroomId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Subject> subjects = assignSubjectService.findSubjectByClassroomId(classroomId);
        if (subjects == null || subjects.isEmpty()) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }

        List<SubjectDto> subjectDtos = subjects.stream()
            .map(subject -> {
                Long subjectId = subject.getSubjectId();
                Assignment assignment = assignmentService.getByClassroomIdAndSubjectId(classroomId, subjectId);

                if(assignment == null) {
                    Lecturer lecturer = new Lecturer();
                    return SubjectMapper.mapToDto(subject, assignment, lecturer);
                } else {
                    Long assignmentId = assignment.getAssignmentId();
                    Lecturer lecturer = lecturerService.findByAssignmentId(assignmentId);
                    return SubjectMapper.mapToDto(subject, assignment, lecturer);
                }
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(subjectDtos);
    }

    @PostMapping("/get-lecturer-free")
    public ResponseEntity<Set<LecturerDto>> showLecturers(@RequestBody CheckAssignmentDto checkAssignmentDto) {
        if(checkAssignmentDto == null) {
            throw new NullPointerException("checkAssignmentDto null!");
        }
        String beginDate = checkAssignmentDto.getBeginDate();
        String endDate = checkAssignmentDto.getEndDate();

        String beginTime = checkAssignmentDto.getBeginTime();
        String endTime = checkAssignmentDto.getEndTime();

        String label = checkAssignmentDto.getLabel();

        Integer dayOfWeek = checkAssignmentDto.getDayOfWeek();

        Set<LecturerDto> listLecturerDtos = lecturerService.findLecturerFreeAtTime(beginDate, endDate, beginTime, endTime, label, dayOfWeek);
        return ResponseEntity.ok(listLecturerDtos);
    }
}
