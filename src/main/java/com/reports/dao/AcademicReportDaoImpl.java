package com.reports.dao;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.reports.entity.AcademicReport;

@Repository
public class AcademicReportDaoImpl extends AbstractDaoImpl<AcademicReport> implements AcademicReportDao {

    @SuppressWarnings("deprecation")
    public AcademicReport findAcademicReport(String year, String standard) {

        List<Criteria> criterias = new ArrayList<Criteria>();
        Query<AcademicReport> query = getQuery();
        query = query.order("-total").limit(1);

        Criteria examTypeCriteria = query.criteria("year").equal(year);
        Criteria standardCriteria = query.criteria("standard").equal(standard);
        criterias.add(examTypeCriteria);
        criterias.add(standardCriteria);

        List<AcademicReport> report = super.find(query, criterias);
        if (null != report && !report.isEmpty())
            return report.get(0);

        return null;

    }

}
