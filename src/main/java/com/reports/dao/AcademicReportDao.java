package com.reports.dao;

import com.reports.entity.AcademicReport;

public interface AcademicReportDao extends BaseDao<AcademicReport> {
    public AcademicReport findAcademicReport(String year, String standard);
}
