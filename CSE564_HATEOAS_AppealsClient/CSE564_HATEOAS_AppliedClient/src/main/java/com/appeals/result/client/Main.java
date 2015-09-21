package com.appeals.result.client;

import static com.appeals.result.model.AppealBuilder.appeal;

import java.net.URI;
import java.net.URISyntaxException;

import com.appeals.result.client.activities.Actions;
import com.appeals.result.client.activities.PlaceAppealActivity;
import com.appeals.result.client.activities.ReadAppealActivity;
import com.appeals.result.client.activities.UpdateAppealActivity;
import com.appeals.result.model.Appeal;
import com.appeals.result.model.AppealStatus;
import com.appeals.result.representations.Link;
import com.appeals.result.representations.AppealRepresentation;
import com.appeals.result.representations.AppealUri;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    
    private static final String APPEALS_MEDIA_TYPE = "application/vnd.cse564-appeals+xml";
    private static final long ONE_MINUTE = 60000; 
    
    private static final String ENTRY_POINT_URI = "http://localhost:8080/CSE564_HATEAOS_AppealServer/webresources/appeal";

    public static void main(String[] args) throws Exception {
        URI serviceUri = new URI(ENTRY_POINT_URI);
        
        System.out.println(String.format("-----------------------------------------"));
        System.out.println(String.format("HAPPY PATH TEST"));
        System.out.println(String.format("-----------------------------------------"));
        System.out.println(String.format(" "));
        happyPathTest(serviceUri);
        System.out.println(String.format(" "));
        System.out.println(String.format("-----------------------------------------"));
        System.out.println(String.format("ABANDONED TEST"));
        System.out.println(String.format("-----------------------------------------"));
        System.out.println(String.format(" "));
        abandonedTest(serviceUri);
        System.out.println(String.format(" "));
        System.out.println(String.format("-----------------------------------------"));
        System.out.println(String.format("FORGOTTEN TEST"));
        System.out.println(String.format("-----------------------------------------"));
        System.out.println(String.format(" "));
        forgottenTest(serviceUri);
        System.out.println(String.format(" "));
        System.out.println(String.format("-----------------------------------------"));
        System.out.println(String.format("BAD START TEST"));
        System.out.println(String.format("-----------------------------------------"));
        System.out.println(String.format(" "));
        BadStartTest(serviceUri);
        System.out.println(String.format(" "));
        System.out.println(String.format("-----------------------------------------"));
        System.out.println(String.format("BAD ID TEST"));
        System.out.println(String.format("-----------------------------------------"));
        System.out.println(String.format(" "));
        BadIDTest(serviceUri);
    }

    private static void hangAround(long backOffTimeInMillis) {
        try {
            Thread.sleep(backOffTimeInMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void happyPathTest(URI serviceUri) throws Exception {
        LOG.info("Starting Happy Path Test with Service URI {}", serviceUri);
        // Place the appeal
        LOG.info("Step 1. The Grades are posted");
        System.out.println(String.format("Happy Path Starts"));
        Appeal appeal = appeal().withfirsttimeRandomItems().build();
        LOG.debug("The Grades Posted : {}",appeal);
        
        LOG.info("Step 2. Make the appeal");
        System.out.println(String.format("Making appeal at [%s] via POST", serviceUri.toString()));
        appeal = appeal().withRandomItems().build();
        
        LOG.debug("Created base appeal {}", appeal);
        Client client = Client.create();
        LOG.debug("Created client {}", client);
        AppealRepresentation appealRepresentation = client.resource(serviceUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        LOG.debug("Created appealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("Appeal placed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        
        // Update the appeal
        LOG.debug("\n\nStep 3. Update the appeal");
        System.out.println(String.format("About to update appeal at [%s] via POST", appealRepresentation.getUpdateLink().getUri().toString()));
        appeal = appeal().withRandomItems().build();
        LOG.debug("Created base appeal {}", appeal);
        Link updateLink = appealRepresentation.getUpdateLink();
        LOG.debug("Created appeal update link {}", updateLink);
        AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(updateLink.getMediaType()).type(updateLink.getMediaType()).post(AppealRepresentation.class, new AppealRepresentation(appeal));
        LOG.debug("Created updated appeal representation link {}", updatedRepresentation);
        System.out.println(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));
        
        //Response from Professor
        LOG.debug("\n\nStep 4. Response from Professor");
        System.out.println(String.format("Getting the response from [%s] via GET.", appealRepresentation.getSelfLink().getUri().toString()));
        Link orderLink = appealRepresentation.getSelfLink();
        ClientResponse Response = client.resource(orderLink.getUri()).delete(ClientResponse.class);
        if(Response.getStatus()==200)
        {System.out.println(String.format("Response from Professor: Appeal Accepted"));}
        else
        {System.out.println(String.format("Response from Professor: Not Accepted"));}
        
        
    }
    private static void abandonedTest(URI serviceUri) throws Exception {
        LOG.info("Starting Abandoned Test with Service URI {}", serviceUri);
        // Place the appeal
        LOG.info("Step 1. The Grades are posted");
        System.out.println(String.format("Abandoned Test Starts"));
        Appeal appeal = appeal().withfirsttimeRandomItems().build();
        LOG.debug("The Grades Posted : {}",appeal);
        
        LOG.info("Step 2. Make the appeal");
        System.out.println(String.format("Making appeal at [%s] via POST", serviceUri.toString()));
        appeal = appeal().withRandomItems().build();
        LOG.debug("Created base order {}", appeal);
        Client client = Client.create();
        LOG.debug("Created client {}", client);
        AppealRepresentation appealRepresentation = client.resource(serviceUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        LOG.debug("Created appealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("appeal placed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
       
         // Delete the appeal
        LOG.debug("\n\nStep 3. Delete the appeal");
        System.out.println(String.format("Deleting the appeal from [%s] via DELETE.", appealRepresentation.getSelfLink().getUri().toString()));
        Link orderLink = appealRepresentation.getSelfLink();
        ClientResponse finalResponse = client.resource(orderLink.getUri()).delete(ClientResponse.class);
        System.out.println(String.format("Status [%d]", finalResponse.getStatus()));
    }

    private static void forgottenTest(URI serviceUri) throws Exception {
        LOG.info("Starting Forgotten Test with Service URI {}", serviceUri);
        // Place the appeal
        LOG.info("Step 1. The Grades are posted");
        System.out.println(String.format("Forgotten Path Starts"));
        Appeal appeal = appeal().withfirsttimeRandomItems().build();
        LOG.debug("The Grades Posted : {}",appeal);
        
        LOG.info("Step 2. Make the appeal");
        System.out.println(String.format("Making appeal at [%s] via POST", serviceUri.toString()));
        appeal = appeal().withRandomItems().build();
        LOG.debug("Created base order {}", appeal);
        Client client = Client.create();
        LOG.debug("Created client {}", client);
        AppealRepresentation appealRepresentation = client.resource(serviceUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        LOG.debug("Created appealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("appeal placed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
       
         // Get the appeal
        LOG.debug("\n\nStep 3. Get the status of the appeal");
        System.out.println(String.format("About to check appeal status at [%s] via GET", appealRepresentation.getSelfLink().getUri().toString()));
        Link orderLink = appealRepresentation.getSelfLink();
        AppealRepresentation finalOrderRepresentation = client.resource(orderLink.getUri()).accept(APPEALS_MEDIA_TYPE).get(AppealRepresentation.class);
        System.out.println(String.format("current status [%s]", finalOrderRepresentation.getStatus()));
        
  
        //Resend the appeal
        LOG.info("Step 4. Resend the appeal");
        System.out.println(String.format("Resend appeal at [%s] via POST", serviceUri.toString()));
        AppealRepresentation resendRepresentation = client.resource(serviceUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println(String.format("appeal resent at [%s]", resendRepresentation.getSelfLink().getUri().toString()));
       
    }
    private static void BadStartTest(URI serviceUri) throws Exception {
        LOG.info("Starting Bad Start Test with Service URI {}", serviceUri);
        
        // Place the appeal
        LOG.info("Step 1. The Grades are posted");
        System.out.println(String.format("Bad Start Path Starts"));
        Appeal appeal = appeal().withfirsttimeRandomItems().build();
        LOG.debug("The Grades Posted : {}",appeal);
        
        LOG.info("Step 2. Make the appeal");
        System.out.println(String.format("Making appeal at [%s] via POST", serviceUri.toString()));
        appeal = appeal().withRandomItems().build();
        LOG.debug("Created base order {}", appeal);
        Client client = Client.create();
        LOG.debug("Created client {}", client);
        AppealRepresentation appealRepresentation = client.resource(serviceUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        LOG.debug("Created appealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("appeal placed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        
        // Try to update a different appeal
        LOG.info("\n\nStep 3. Try to update the appeal with wrong IDs");
        System.out.println(String.format("About to update an appeal with bad URI [%s] via POST", appealRepresentation.getUpdateLink().getUri().toString() + "/bad-uri"));
        appeal = appeal().withRandomItems().build();
        LOG.debug("Created base appeal {}", appeal);
        Link badLink = new Link("bad", new AppealUri(appealRepresentation.getSelfLink().getUri().toString() + "/bad-uri"), APPEALS_MEDIA_TYPE);
        LOG.debug("Create bad link {}", badLink);
        ClientResponse badUpdateResponse = client.resource(badLink.getUri()).accept(badLink.getMediaType()).type(badLink.getMediaType()).post(ClientResponse.class, new AppealRepresentation(appeal));
        LOG.debug("Created Bad Update Response {}", badUpdateResponse);
        System.out.println(String.format("Tried to update appeal with bad URI at [%s] via POST, outcome [%d]", badLink.getUri().toString(), badUpdateResponse.getStatus()));
                
    }
    
    private static void BadIDTest(URI serviceUri) throws Exception {
        LOG.info("Starting Bad Id Test with Service URI {}", serviceUri);
        // Place the appeal
        LOG.info("Step 1. The Grades are posted");
        System.out.println(String.format("Bad Id Path Starts"));
        Appeal appeal = appeal().withRandomItems().build();
        LOG.debug("The Grades Posted : {}",appeal);
        
        LOG.info("Step 2. Make the appeal");
        System.out.println(String.format("Making appeal at [%s] via POST", serviceUri.toString()));
        appeal = appeal().withRandomItems().build();
        LOG.debug("Created base order {}", appeal);
        Client client = Client.create();
        LOG.debug("Created client {}", client);
        AppealRepresentation appealRepresentation = client.resource(serviceUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        LOG.debug("Created appealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("appeal placed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
       
         // Get the appeal status
        LOG.debug("\n\nStep 3. Get the status of the appeal");
        System.out.println(String.format("About to check appeal status at [%s] via GET", appealRepresentation.getSelfLink().getUri().toString()));
        Link orderLink = appealRepresentation.getSelfLink();
        AppealRepresentation finalOrderRepresentation = client.resource(orderLink.getUri()).accept(APPEALS_MEDIA_TYPE).get(AppealRepresentation.class);
        System.out.println(String.format("current status [%s]", finalOrderRepresentation.getStatus()));
           
        //Resend the appeal
        LOG.info("Step 4. Resend the appeal with wrong id ");
        System.out.println(String.format("Resend appeal at [%s] via POST", serviceUri.toString()));
        Link badLink = new Link("bad", new AppealUri(appealRepresentation.getSelfLink().getUri().toString() + "/bad-uri"), APPEALS_MEDIA_TYPE);
        ClientResponse badUpdateResponse = client.resource(badLink.getUri()).accept(badLink.getMediaType()).type(badLink.getMediaType()).post(ClientResponse.class, new AppealRepresentation(appeal));
        LOG.debug("Created Bad Update Response {}", badUpdateResponse);
        System.out.println(String.format("Tried to resend appeal with bad ID at [%s] via POST, outcome [%d]", badLink.getUri().toString(), badUpdateResponse.getStatus()));       
    }
}
