package com.reports.entity;

import java.text.DecimalFormat;
import java.util.Map;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "studentMarks")
@Indexes(@Index(fields = { @Field(value = "rollNo"),
        @Field(value = "timestamp"), @Field(value = "standard"),
        @Field(value = "timestamp") }, options = @IndexOptions(name = "studentMarks_Index")))
public class StudentMarks {

    @Id
    private String id;
    private String rollNo;
    private String status;
    private String standard;
    private String examType;
    private double percentage;
    private double total;
    private long timestamp;
    private Map<String, Double> marks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.percentage = Double.valueOf(df.format(percentage));
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public Map<String, Double> getMarks() {
        return marks;
    }

    public void setMarks(Map<String, Double> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StudentMarks [id=");
        builder.append(id);
        builder.append(", rollNo=");
        builder.append(rollNo);
        builder.append(", status=");
        builder.append(status);
        builder.append(", standard=");
        builder.append(standard);
        builder.append(", examType=");
        builder.append(examType);
        builder.append(", percentage=");
        builder.append(percentage);
        builder.append(", total=");
        builder.append(total);
        builder.append(", timestamp=");
        builder.append(timestamp);
        builder.append(", marks=");
        builder.append(marks);
        builder.append("]");
        return builder.toString();
    }
}
