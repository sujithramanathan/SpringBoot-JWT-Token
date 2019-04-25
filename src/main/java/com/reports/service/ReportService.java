package com.reports.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mongodb.morphia.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.DuplicateKeyException;
import com.reports.dao.AcademicReportDao;
import com.reports.dao.StudentMarksDao;
import com.reports.dao.UsersDao;
import com.reports.entity.AcademicReport;
import com.reports.entity.StudentMarks;
import com.reports.entity.Users;
import com.reports.util.Constants;
import com.reports.util.ReportUtil;

@Service
public class ReportService {

    private final Logger logger = LoggerFactory.getLogger(ReportService.class);

    @Value("${" + Constants.STUDENT_PASS_MARK_MIN + ":40}")
    private double passMark;

    @Autowired
    private UsersDao studentDAO;

    @Autowired
    private StudentMarksDao studentMarksDAO;

    @Autowired
    private UsersDao usersDAO;

    @Autowired
    private AcademicReportDao academicReportDAO;

    @Autowired
    private ReportUtil util;

    public Users findByUserId(String userId) {
        Users user = null;
        try {
            user = usersDAO.findById(userId);
        } catch (Exception e) {
            logger.error("Error occurred while fetching findByUserId userId={}", userId, e);
        }
        return user;
    }

    public boolean saveStudentMarks(StudentMarks entity) {
        entity.setStatus("pass");
        double total = 0;
        int noOfSubjects = 0;
        for (Entry<String, Double> mark : entity.getMarks().entrySet()) {
            total += mark.getValue();
            noOfSubjects++;
            if (mark.getValue() < passMark) {
                entity.setStatus("fail");
            }
        }
        entity.setTotal(total);
        entity.setPercentage(total / noOfSubjects);
        try {
            studentMarksDAO.save(entity);
        } catch (Exception e) {
            logger.error("Exception occurred while saving studentMarks rollNumber={} {}", entity.getRollNo(), e);
            return false;
        }
        return true;
    }

    public ResponseEntity<String> saveStudent(Users student) {

        String id = null;
        try {
            Key<Users> key = studentDAO.save(student);
            id = key != null ? (String) key.getId() : null;
        } catch (DuplicateKeyException dke) {
            logger.error("DuplicateKeyException occurred {} ", dke);
            return new ResponseEntity<String>("Record Already exists for userId " + student.getRollNo(), HttpStatus.CONFLICT);
        } catch (Exception me) {
            logger.error("Exception occurred {} ", me);
            return new ResponseEntity<String>("Record not saved ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (student.getRollNo().equals(id)) {
            return new ResponseEntity<String>("Record Created", HttpStatus.CREATED);
        }
        else {
            logger.debug("Id's are not matched id={}, student.getRollNo()={} ", id, student.getRollNo());
            return new ResponseEntity<String>("Failed to save record ", HttpStatus.FORBIDDEN);
        }
    }

    public List<StudentMarks> getStudentMarks(String id) {
        List<StudentMarks> marks = null;
        try {
            marks = studentMarksDAO.findByFieldNameAndValue("rollNo", id);
        } catch (Exception e) {
            logger.error("Exception occurred while getting student marks for rollNo " + id + " {} ", e);
        }
        return marks;
    }

    public List<StudentMarks> getAcademicYearTopper(String academicYear, String standard) throws ParseException {

        long starTimeOfAcademicYear = util.getTimeStamp(academicYear.concat("/01/01 00:00:00"));
        long endTimeOfAcademicYear = util.getTimeStamp(academicYear.concat("/12/31 23:59:59"));

        logger.debug("starTimeOfAcademicYear={}, endTimeOfAcademicYear={}, standard={}", starTimeOfAcademicYear, endTimeOfAcademicYear,
                standard);

        return studentMarksDAO.findAcademicTopper(starTimeOfAcademicYear, endTimeOfAcademicYear, standard);

    }

    public List<StudentMarks> getAcademicSubjectTopper(String academicYear, String subject, String standard) throws ParseException {

        long starTimeOfAcademicYear = util.getTimeStamp(academicYear.concat("/01/01 00:00:00"));
        long endTimeOfAcademicYear = util.getTimeStamp(academicYear.concat("/12/31 23:59:59"));

        logger.debug("starTimeOfAcademicYear={}, endTimeOfAcademicYear={}, subject={}, standard={}", starTimeOfAcademicYear,
                endTimeOfAcademicYear, subject, standard);

        return studentMarksDAO.findSubjectTopper(starTimeOfAcademicYear, endTimeOfAcademicYear, subject, standard);
    }

    public boolean initiateAcademicReport(String academicYear, String standard) throws ParseException {

        long starTimeOfAcademicYear = util.getTimeStamp(academicYear.concat("/01/01 00:00:00"));
        long endTimeOfAcademicYear = util.getTimeStamp(academicYear.concat("/12/31 23:59:59"));

        List<StudentMarks> academicMarks = studentMarksDAO.findByAcademicYearAndStandard(starTimeOfAcademicYear, endTimeOfAcademicYear,
                standard);

        int passCount = 0;
        double studentsCount = 0;
        Map<String, Double> failedSubjectMap = new HashMap<String, Double>();
        AcademicReport academicReport = null;

        if (null != academicMarks && !academicMarks.isEmpty()) {
            academicReport = new AcademicReport();
            academicReport.setYear(academicYear);
            academicReport.setStandard(standard);
            studentsCount = academicMarks.size();
            for (StudentMarks marks : academicMarks) {
                if (academicReport.getAcademicTopper() < marks.getTotal()) {
                    academicReport.setAcademicTopper(marks.getTotal());
                }
                if (Constants.FAIL_STATUS.equals(marks.getStatus())) {
                    for (Entry<String, Double> subject : marks.getMarks().entrySet()) {
                        if (subject.getValue() < passMark) {
                            Double countPerSubject = failedSubjectMap.get(subject.getKey());
                            if (null != countPerSubject) {
                                failedSubjectMap.put(subject.getKey(), ++countPerSubject);
                            }
                            else {
                                failedSubjectMap.put(subject.getKey(), 1.0);
                            }
                        }
                    }
                }
                else {
                    passCount++;
                }
            }
            academicReport.setPassPercentage(util.roundOff((passCount / studentsCount) * 100));
            academicReport.setFailuresPerSubject(failedSubjectMap);
        }
        if (null != academicReport) {
            academicReportDAO.save(academicReport);
            return true;
        }
        return false;
    }

    public ResponseEntity<Object> getAcademicReport(String year, String standard) {

        AcademicReport academicReport = academicReportDAO.findAcademicReport(year, standard);

        if (null != academicReport) {
            return new ResponseEntity<>(academicReport, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("No Reports Found, Please initiate report.", HttpStatus.OK);
        }
    }

}
