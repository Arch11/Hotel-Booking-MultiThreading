package com.appeals.result.representations;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.appeals.result.activities.InvalidAppealException;
import com.appeals.result.activities.UriExchange;
import com.appeals.result.model.Report;
import com.appeals.result.model.Comment;
import com.appeals.result.model.Appeal;
import com.appeals.result.model.AppealStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "appeal", namespace = Representation.APPEALS_NAMESPACE)
public class AppealRepresentation extends Representation {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealRepresentation.class);

    @XmlElement(name = "item", namespace = Representation.APPEALS_NAMESPACE)
    private List<Report> items;
    @XmlElement(name = "comment", namespace = Representation.APPEALS_NAMESPACE)
    private Comment comment;
    @XmlElement(name = "status", namespace = Representation.APPEALS_NAMESPACE)
    private AppealStatus status;

    /**
     * For JAXB :-(
     */
    AppealRepresentation() {
        LOG.info("Executing AppealRepresentation constructor");
    }

    public static AppealRepresentation fromXmlString(String xmlRepresentation) {
        LOG.info("Creating an Appeal object from the XML = {}", xmlRepresentation);
                
        AppealRepresentation appealRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            appealRepresentation = (AppealRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            throw new InvalidAppealException(e);
        }
        
        LOG.debug("Generated the object {}", appealRepresentation);
        return appealRepresentation;
    }
    
    public static AppealRepresentation createResponseAppealRepresentation(Appeal appeal, AppealUri appealUri) {
        LOG.info("Creating a Response Appeal for appeal = {} and appeal URI", appeal.toString(), appealUri.toString());
        
        AppealRepresentation appealRepresentation = null; 
                
        if(appeal.getStatus() == AppealStatus.GENERATING) {
            LOG.debug("The appeal status is {}", AppealStatus.GENERATING);
            appealRepresentation = new AppealRepresentation(appeal, 
                    new Link(RELATIONS_URI + "cancel", appealUri), 
                    new Link(RELATIONS_URI + "update", appealUri),
                    new Link(Representation.SELF_REL_VALUE, appealUri));
        } else if(appeal.getStatus() == AppealStatus.PROCESSING) {
            LOG.debug("The appeal status is {}", AppealStatus.PROCESSING);
            appealRepresentation = new AppealRepresentation(appeal, new Link(Representation.SELF_REL_VALUE, appealUri));
        } else if(appeal.getStatus() == AppealStatus.READY) {
            LOG.debug("The appeal status is {}", AppealStatus.READY);
        } else if(appeal.getStatus() == AppealStatus.DONE) {
            LOG.debug("The appeal status is {}", AppealStatus.DONE);
            appealRepresentation = new AppealRepresentation(appeal);
               // new Link(RELATIONS_URI + "done", appealUri);
        } else {
            LOG.debug("The appeal status is in an unknown status");
            throw new RuntimeException("Unknown Appeal Status");
        }
        
        LOG.debug("The appeal representation created for the Create Response Appeal Representation is {}", appealRepresentation);
        
        return appealRepresentation;
    }

    public AppealRepresentation(Appeal appeal, Link... links) {
        LOG.info("Creating an Appeal Representation for appeal = {} and links = {}", appeal.toString(), links.toString());
        
        try {
            this.comment = appeal.getComment();
            this.items = appeal.getItems();
            this.status = appeal.getStatus();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidAppealException(ex);
        }
        
        LOG.debug("Created the AppealRepresentation {}", this);
    }

    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Appeal getAppeal() {
        if (comment == null || items == null) {
            throw new InvalidAppealException();
        }
        for (Report i : items) {
            if (i == null) {
                throw new InvalidAppealException();
            }
        }

        return new Appeal(comment, status, items);
    }

    public Link getCancelLink() {
        return getLinkByName(RELATIONS_URI + "cancel");
    }

    public Link getUpdateLink() {
        return getLinkByName(RELATIONS_URI + "update");
    }

    public Link getSelfLink() {
        return getLinkByName("self");
    }
    
    public AppealStatus getStatus() {
        return status;
    }
}
