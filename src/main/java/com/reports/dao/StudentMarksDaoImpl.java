package com.reports.dao;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.reports.entity.StudentMarks;

@Repository
@SuppressWarnings("deprecation")
public class StudentMarksDaoImpl extends AbstractDaoImpl<StudentMarks> implements StudentMarksDao {

    public List<StudentMarks> findAcademicTopper(long startOfYear, long endOfYear, String standard) {

        List<Criteria> criterias = new ArrayList<Criteria>();
        Query<StudentMarks> query = getQuery();
        query = query.order("-total").limit(1);

        Criteria startOfYearCriteria = query.criteria("timestamp").greaterThanOrEq(startOfYear);
        Criteria endOfYearCriteria = query.criteria("timestamp").lessThanOrEq(endOfYear);
        Criteria examTypeCriteria = query.criteria("examType").equal("annual");
        Criteria standardCriteria = query.criteria("standard").equal(standard);
        criterias.add(startOfYearCriteria);
        criterias.add(endOfYearCriteria);
        criterias.add(examTypeCriteria);
        criterias.add(standardCriteria);

        return super.find(query, criterias);
    }

    public List<StudentMarks> findSubjectTopper(long startOfYear, long endOfYear, String subject, String standard) {
        subject = "-marks.".concat(subject);
        List<Criteria> criterias = new ArrayList<Criteria>();
        Query<StudentMarks> query = getQuery();
        query = query.order(subject).limit(1);

        Criteria startOfYearCriteria = query.criteria("timestamp").greaterThanOrEq(startOfYear);
        Criteria endOfYearCriteria = query.criteria("timestamp").lessThanOrEq(endOfYear);
        Criteria standardCriteria = query.criteria("standard").equal(standard);
        Criteria examTypeCriteria = query.criteria("examType").equal("annual");
        criterias.add(startOfYearCriteria);
        criterias.add(endOfYearCriteria);
        criterias.add(standardCriteria);
        criterias.add(examTypeCriteria);

        return super.find(query, criterias);
    }

    public List<StudentMarks> findByAcademicYearAndStandard(long startOfYear, long endOfYear, String standard) {

        Query<StudentMarks> query = getQuery();
        Criteria fromCriteria = query.criteria("timestamp").greaterThanOrEq(startOfYear);
        Criteria toCriteria = query.criteria("timestamp").lessThanOrEq(endOfYear);
        Criteria standardCriteria = query.criteria("standard").equal(standard);
        Criteria examTypeCriteria = query.criteria("examType").equal("annual");

        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(fromCriteria);
        criterias.add(toCriteria);
        criterias.add(standardCriteria);
        criterias.add(examTypeCriteria);

        return super.find(query, criterias);
    }

}
