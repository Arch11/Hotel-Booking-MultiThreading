package com.appeals.result.client;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.appeals.result.model.Report;
import com.appeals.result.model.Comment;
import com.appeals.result.model.Appeal;
import com.appeals.result.model.AppealStatus;
import com.appeals.result.representations.Representation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "appeal", namespace = Representation.APPEALS_NAMESPACE)
public class ClientAppeal {
    
    private static final Logger LOG = LoggerFactory.getLogger(ClientAppeal.class);
    
    @XmlElement(name = "item", namespace = Representation.APPEALS_NAMESPACE)
    private List<Report> items;
    @XmlElement(name = "comment", namespace = Representation.APPEALS_NAMESPACE)
    private Comment comment;
    @XmlElement(name = "status", namespace = Representation.APPEALS_NAMESPACE)
    private AppealStatus status;
    
    private ClientAppeal(){}
    
    public ClientAppeal(Appeal appeal) {
        LOG.debug("Executing ClientAppeal constructor");
        this.comment = appeal.getComment();
        this.items = appeal.getItems();
    }
    
    public Appeal getAppeal() {
        LOG.debug("Executing ClientAppeal.getAppeal");
        return new Appeal(comment, status, items);
    }
    
    public Comment getComment() {
        LOG.debug("Executing ClientAppeal.getComment");
        return comment;
    }
    
    public List<Report> getItems() {
        LOG.debug("Executing ClientAppeal.getItems");
        return items;
    }

    @Override
    public String toString() {
        LOG.debug("Executing ClientAppeal.toString");
        try {
            JAXBContext context = JAXBContext.newInstance(ClientAppeal.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public AppealStatus getStatus() {
        LOG.debug("Executing ClientAppeal.getStatus");
        return status;
    }

}