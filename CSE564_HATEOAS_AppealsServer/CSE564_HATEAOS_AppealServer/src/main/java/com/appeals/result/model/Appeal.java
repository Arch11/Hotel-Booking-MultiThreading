package com.appeals.result.model;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

public class Appeal {
    
    private final Comment comment;
    private final List<Report> items;
    @XmlTransient
    private AppealStatus status = AppealStatus.GENERATING;

    public Appeal(Comment comment, List<Report> items) {
      this(comment, AppealStatus.GENERATING, items);
    }
    

    public Appeal(Comment comment, AppealStatus status, List<Report> items) {
        this.comment = comment;
        this.items = items;
        this.status = status;
    }

    public Comment getLocation() {
        return comment;
    }
    
    public List<Report> getItems() {
        return items;
    }

    public void setStatus(AppealStatus status) {
        this.status = status;
    }

    public AppealStatus getStatus() {
        return status;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Location: " + comment + "\n");
        sb.append("Status: " + status + "\n");
        for(Report i : items) {
            sb.append("Item: " + i.toString()+ "\n");
        }
        return sb.toString();
    }
}