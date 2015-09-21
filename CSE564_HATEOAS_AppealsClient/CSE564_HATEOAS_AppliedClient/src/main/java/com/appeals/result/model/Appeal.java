package com.appeals.result.model;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Appeal {
    
    private static final Logger LOG = LoggerFactory.getLogger(Appeal.class);
    
    private final Comment comment;
    private final List<Report> items;
    @XmlTransient
    private AppealStatus status = AppealStatus.GENERATING;

    public Appeal(Comment comment, List<Report> items) {
        this(comment, AppealStatus.GENERATING, items);
        LOG.debug("Executing Appeal constructor: comment = {} and items = {}", comment, items);
    }
    

    public Appeal(Comment comment, AppealStatus status, List<Report> items) {
        this.comment = comment;
        this.items = items;
        this.status = status;
        LOG.debug("Executing Appeal constructor: comment = {}, status = {} and items = {}", comment, status, items);
        LOG.debug("appeal = {}", this);
    }

    public Comment getComment() {
        LOG.debug("Executing Appeal.getComment");
        LOG.debug("comment = {}", comment);
        return comment;
    }
    
    public List<Report> getItems() {
        LOG.debug("Executing Appeal.getItems");
        LOG.debug("Report = {}", items);
        return items;
    }

    public void setStatus(AppealStatus status) {
        LOG.debug("Executing Appeal.setStatus");
        this.status = status;
    }

    public AppealStatus getStatus() {
        LOG.debug("Executing Appeal.getStatus");
        return status;
    }
    
    @Override
    public String toString() {
        LOG.debug("Executing Appeal.toString");
        StringBuilder sb = new StringBuilder();
        sb.append("Comment: " + comment + "\n");
        sb.append("Status: " + status + "\n");
        for(Report i : items) {
            sb.append("Item: " + i.toString()+ "\n");
        }
        return sb.toString();
    }
}