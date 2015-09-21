package com.appeals.result.model;

import static com.appeals.result.model.ReportBuilder.item;

import java.util.ArrayList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppealBuilder {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealBuilder.class);
    
    public static AppealBuilder appeal() {
        return new AppealBuilder();
    }

    private Comment comment = Comment.NO_COMMENTS;
    private ArrayList<Report> items = null;
    private AppealStatus status = AppealStatus.GENERATING;
    
    private void defaultItems() {
        LOG.debug("Executing AppealBuilder.defaultItems");
        ArrayList<Report> items = new ArrayList<Report>();
        items.add(item().build());
        this.items = items;
    }
    
    private void corruptItems() {
        LOG.debug("Executing AppealBuilder.corruptItems");
        ArrayList<Report> items = new ArrayList<Report>();
        items.add(null);
        items.add(null);
        items.add(null);
        items.add(null);
        this.items = items;
    }
   
    
    public Appeal build() {
        LOG.debug("Executing AppealBuilder.build");
        if(items == null) {
            defaultItems();
        }
        return new Appeal(comment, status, items);
    }

    public AppealBuilder withItem(Report item) {
        LOG.debug("Executing AppealBuilder.withItem");
        if(items == null) {
            items = new ArrayList<Report>();
        }
        items.add(item);
        return this;
    }


    public AppealBuilder withCorruptedValues() {
        LOG.debug("Executing AppealBuilder.withCorruptedValues");
        corruptItems();
        return this;
    }
    
    public AppealBuilder withStatus(AppealStatus status) {
        LOG.debug("Executing AppealBuilder.withRandomItems");
        this.status = status;
        return this;
    }

    public AppealBuilder withfirsttimeRandomItems() {
        LOG.debug("Executing AppealBuilder.withfirsttimeRandomItems");
        this.items = new ArrayList<Report>();
            items.add(item().random().build());
        return this;
    }
    public AppealBuilder withRandomItems() {
        LOG.debug("Executing AppealBuilder.withRandomItems");
        this.items = new ArrayList<Report>();
            items.add(item().random().build());
         Random r = new Random();
         comment = Comment.values()[r.nextInt(Comment.values().length)];
        return this;
    }

}
