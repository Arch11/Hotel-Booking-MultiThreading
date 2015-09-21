package com.appeals.result.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.appeals.result.representations.Representation;

@XmlRootElement
public class Report {
    @XmlElement(namespace = Representation.APPEALS_NAMESPACE)
    private Grade grade;
    
    /**
     * For JAXB :-(
     */
    Report(){}
    
    public Report(Grade grade) {
        this.grade = grade;       
    }
    
    public Grade getGrade() {
        return grade;
    }

    public String toString() {
        return " " + grade + " ";
    }
}