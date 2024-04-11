package com.example.springtraining.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import com.example.springtraining.service.AssignmentService;
import com.example.springtraining.service.LecturerService;
import com.example.springtraining.service.SubjectService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/course-management/admin/assign-classroom")
@CrossOrigin(origins = "http://localhost:8081")
public class AssignSubjectController {;

    private final LecturerService lecturerService;

    private final SubjectService subjectService;

    private final AssignmentService assignmentService;

    public AssignSubjectController(LecturerService lecturerService, SubjectService subjectService, AssignmentService assignmentService) {
        this.lecturerService = lecturerService;
        this.subjectService = subjectService;
        this.assignmentService = assignmentService;
    } 

    @GetMapping("/{classroomId}")
    public ResponseEntity<List<SubjectDto>> getSubjecOfClass(@PathVariable(name = "classroomId") Long classroomId) {
        if(classroomId == null) {
            return ResponseEntity.badRequest().build();
        } else {
            List<SubjectDto> subjectDtos = subjectService.getSubjectOfClass(classroomId);
            return ResponseEntity.ok(subjectDtos);
        }
    }

    // find freedom lecturer
    @PostMapping("/get-lecturer-free")
    public ResponseEntity<List<LecturerDto>> showLecturers(@RequestBody CheckAssignmentDto checkAssignmentDto) {
        if (checkAssignmentDto == null) {
            throw new NullPointerException("checkAssignmentDto is null!");
        }

        List<LecturerDto> lecturerDtos = lecturerService.findLecturerFreeAtTime(checkAssignmentDto);
        return ResponseEntity.ok(lecturerDtos);
    }

    @PutMapping("/save-assignment")
    public ResponseEntity<?> saveAssignment(@RequestBody CheckAssignmentDto checkAssignmentDto) {
        if(checkAssignmentDto == null) {
            throw new NullPointerException("checkAssignmentDto is null!");
        } else {
            assignmentService.updateAssignment(checkAssignmentDto);
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }
}
