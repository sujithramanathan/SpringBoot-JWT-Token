package com.reports.dao;

import java.util.List;

import com.reports.entity.StudentMarks;

public interface StudentMarksDao extends BaseDao<StudentMarks> {

    public List<StudentMarks> findAcademicTopper(long startOfYear, long endOfYear, String standard);

    public List<StudentMarks> findSubjectTopper(long startOfYear, long endOfYear, String subject, String standard);

    public List<StudentMarks> findByAcademicYearAndStandard(long startOfYear, long endOfYear, String standard);

}
