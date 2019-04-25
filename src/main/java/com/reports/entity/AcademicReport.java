package com.reports.entity;

import java.util.Map;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("academicReport")
public class AcademicReport {

    @Id
    private String id;

    private String year;

    private String standard;

    private double passPercentage;

    private double academicTopper;

    private Map<String, Double> failuresPerSubject;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getPassPercentage() {
        return passPercentage;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public void setPassPercentage(double passPercentage) {
        this.passPercentage = passPercentage;
    }

    public double getAcademicTopper() {
        return academicTopper;
    }

    public void setAcademicTopper(double topperTotal) {
        this.academicTopper = topperTotal;
    }

    public Map<String, Double> getFailuresPerSubject() {
        return failuresPerSubject;
    }

    public void setFailuresPerSubject(Map<String, Double> failuresPerSubject) {
        this.failuresPerSubject = failuresPerSubject;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AcademicReport [year=");
        builder.append(year);
        builder.append(", standard=");
        builder.append(standard);
        builder.append(", passPercentage=");
        builder.append(passPercentage);
        builder.append(", academicTopper=");
        builder.append(academicTopper);
        builder.append(", failedSubjects=");
        builder.append(failuresPerSubject);
        builder.append("]");
        return builder.toString();
    }
}
