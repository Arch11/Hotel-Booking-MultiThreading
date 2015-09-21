package com.appeals.result.model;

import java.util.Random;


public class ReportBuilder {
    public static ReportBuilder item() {
        return new ReportBuilder();
    }

    private Grade grade = Grade.B;    
    
    public Report build() {
        return new Report(grade);
    }
    
    public ReportBuilder withGrade(Grade grade) {
        this.grade  = grade;
        return this;
    }

    public ReportBuilder random() {
        Random r = new Random();
        grade = Grade.values()[r.nextInt(Grade.values().length)];
        return this;
    }
}
