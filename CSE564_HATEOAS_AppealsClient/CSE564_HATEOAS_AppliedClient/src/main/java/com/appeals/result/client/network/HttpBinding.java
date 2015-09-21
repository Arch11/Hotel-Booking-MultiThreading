package com.appeals.result.client.network;

import java.net.URI;

import com.appeals.result.client.activities.CannotCancelException;
import com.appeals.result.client.activities.CannotUpdateAppealException;
import com.appeals.result.client.activities.MalformedAppealException;
import com.appeals.result.client.activities.NotFoundException;
import com.appeals.result.client.activities.ServiceFailureException;
import com.appeals.result.model.Appeal;
import com.appeals.result.representations.AppealRepresentation;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class HttpBinding {

    private static final String APPEALS_MEDIA_TYPE = "application/vnd.cse564-appeals+xml";

    public AppealRepresentation createAppeal(Appeal appeal, URI appealUri) throws MalformedAppealException, ServiceFailureException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(ClientResponse.class, new AppealRepresentation(appeal));

        int status = response.getStatus();

        if (status == 400) {
            throw new MalformedAppealException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 201) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response [%d] while creating appeal resource [%s]", status, appealUri.toString()));
    }
    
    public AppealRepresentation retrieveAppeal(URI appealUri) throws NotFoundException, ServiceFailureException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(APPEALS_MEDIA_TYPE).get(ClientResponse.class);

        int status = response.getStatus();

        if (status == 404) {
            throw new NotFoundException ();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 200) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response while retrieving appeal resource [%s]", appealUri.toString()));
    }

    public AppealRepresentation updateAppeal(Appeal appeal, URI appealUri) throws MalformedAppealException, ServiceFailureException, NotFoundException,
            CannotUpdateAppealException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(ClientResponse.class, new AppealRepresentation(appeal));

        int status = response.getStatus();

        if (status == 400) {
            throw new MalformedAppealException();
        } else if (status == 404) {
            throw new NotFoundException();
        } else if (status == 409) {
            throw new CannotUpdateAppealException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 200) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response [%d] while udpating appeal resource [%s]", status, appealUri.toString()));
    }

    public AppealRepresentation deleteAppeal(URI appealUri) throws ServiceFailureException, CannotCancelException, NotFoundException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(APPEALS_MEDIA_TYPE).delete(ClientResponse.class);

        int status = response.getStatus();
        if (status == 404) {
            throw new NotFoundException();
        } else if (status == 405) {
            throw new CannotCancelException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 200) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response [%d] while deleting appeal resource [%s]", status, appealUri.toString()));
    }
/*
    public PaymentRepresentation makePayment(Payment payment, URI paymentUri) throws InvalidPaymentException, NotFoundException, DuplicatePaymentException,
            ServiceFailureException {
        Client client = Client.create();
        ClientResponse response = client.resource(paymentUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).put(ClientResponse.class, new PaymentRepresentation(payment));

        int status = response.getStatus();
        if (status == 400) {
            throw new InvalidPaymentException();
        } else if (status == 404) {
            throw new NotFoundException();
        } else if (status == 405) {
            throw new DuplicatePaymentException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 201) {
            return response.getEntity(PaymentRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response [%d] while creating payment resource [%s]", status, paymentUri.toString()));
    }

    public ReceiptRepresentation retrieveReceipt(URI receiptUri) throws NotFoundException, ServiceFailureException {
        Client client = Client.create();
        ClientResponse response = client.resource(receiptUri).accept(APPEALS_MEDIA_TYPE).get(ClientResponse.class);

        int status = response.getStatus();
        if (status == 404) {
            throw new NotFoundException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 200) {
            return response.getEntity(ReceiptRepresentation.class);
        }
        
        throw new RuntimeException(String.format("Unexpected response [%d] while retrieving receipt resource [%s]", status, receiptUri.toString()));
    }*/
}
