package com.appeals.result.client.activities;

import java.net.URI;

import com.appeals.result.client.ClientAppeal;
import com.appeals.result.model.Appeal;
import com.appeals.result.representations.AppealRepresentation;

public class PlaceAppealActivity extends Activity {

    private Appeal appeal;

    public void placeAppeal(Appeal appeal, URI appealUri) {
        
        try {
            AppealRepresentation createdAppealRepresentation = binding.createAppeal(appeal, appealUri);
            this.actions = new RepresentationHypermediaProcessor().extractNextActionsFromAppealRepresentation(createdAppealRepresentation);
            this.appeal = createdAppealRepresentation.getAppeal();
        } catch (MalformedAppealException e) {
            this.actions = retryCurrentActivity();
        } catch (ServiceFailureException e) {
            this.actions = retryCurrentActivity();
        }
    }
    
    public ClientAppeal getAppeal() {
        return new ClientAppeal(appeal);
    }
}
