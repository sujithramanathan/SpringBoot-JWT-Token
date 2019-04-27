package com.reports.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reports.service.ReportService;

@RestController
@RequestMapping("/v1/academic/reports")
public class AcademicController {

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/topper/year/{academicYear}/standard/{standard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAcademicTopper(@PathVariable String academicYear, @PathVariable String standard)
            throws ParseException {

        standard = standard.toUpperCase();

        return new ResponseEntity<>(reportService.getAcademicYearTopper(academicYear, standard), HttpStatus.OK);
    }

    @GetMapping(value = "/topper/year/{academicYear}/subject/{subject}/standard/{standard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getSubjectTopper(@PathVariable String academicYear, @PathVariable String subject,
            @PathVariable String standard) throws ParseException {

        standard = standard.toUpperCase();
        return new ResponseEntity<>(reportService.getAcademicSubjectTopper(academicYear, subject, standard), HttpStatus.OK);

    }

    @PostMapping(value = "/year/{academicYear}/standard/{standard}")
    public ResponseEntity<Object> generateAcademicReport(@PathVariable String academicYear,
            @PathVariable String standard) throws ParseException {

        standard = standard.toUpperCase();
        if (reportService.initiateAcademicReport(academicYear, standard)) {
            return new ResponseEntity<>("Report Created ", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Unable to create content ", HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping(value = "/year/{academicYear}/standard/{standard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAcademicReport(@PathVariable String academicYear,
            @PathVariable String standard) throws ParseException {

        standard = standard.toUpperCase();

        return reportService.getAcademicReport(academicYear, standard);

    }

}
