package com.reports.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reports.entity.StudentMarks;
import com.reports.service.ReportService;
import com.reports.util.Constants;

@RestController
@RequestMapping("/v1/reports")
public class ReportsController {

    private final Logger logger = LoggerFactory.getLogger(ReportsController.class);

    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasAuthority('" + Constants.ADMIN_ROLE + "')")
    @PostMapping("/post/marks")
    public ResponseEntity<String> saveMarks(@RequestBody StudentMarks studentMarks) {

        boolean saveFlag = reportService.saveStudentMarks(studentMarks);

        if (saveFlag) {
            return new ResponseEntity<String>("Created", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<String>("Record not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/get/marks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getStudentMark(Authentication authentication) {

        logger.debug("Student Id = {}", authentication.getName());
        List<StudentMarks> marks = reportService.getStudentMarks(authentication.getName());

        if (null == marks || marks.isEmpty()) {
            return new ResponseEntity<>("No Data Found", HttpStatus.OK);
        }

        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Constants.ADMIN_ROLE + "')")
    @GetMapping(value = "/get/marks/{studentRollNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getStudentMarkById(@PathVariable String studentRollNo) {

        logger.debug("Student Id = {}", studentRollNo);
        List<StudentMarks> marks = reportService.getStudentMarks(studentRollNo);

        if (null == marks || marks.isEmpty()) {
            return new ResponseEntity<>("Invalid Student Id " + studentRollNo, HttpStatus.OK);
        }

        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Constants.STUDENT_ROLE + "')")
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> test(Authentication authentication) {

        return new ResponseEntity<>("Okay!!!!!", HttpStatus.OK);
    }

}
