package com.scb.api.ratelimit.student.controller;

import com.scb.api.ratelimit.student.common.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1")
public class StudentController {

    @PostMapping("student")
    public ResponseEntity<String> createStudent(@RequestBody StudentDto studentDto) {

        return new ResponseEntity(HttpStatus.CREATED);
    }


    @GetMapping("student/{studentId}")
    public ResponseEntity<String> getStudentById(@PathVariable Integer studentId) {

        StudentDto studentDto = new StudentDto();
        studentDto.setStudentId(studentId);
        studentDto.setStudentName("Andrew");
        studentDto.setStudentMarks(98.5);

        return new ResponseEntity(HttpStatus.FOUND);
    }
}