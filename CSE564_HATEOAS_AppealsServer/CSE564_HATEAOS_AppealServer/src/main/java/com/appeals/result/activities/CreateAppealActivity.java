package com.appeals.result.activities;

import com.appeals.result.model.Identifier;
import com.appeals.result.model.Appeal;
import com.appeals.result.model.AppealStatus;
import com.appeals.result.repositories.AppealRepository;
import com.appeals.result.representations.Link;
import com.appeals.result.representations.AppealRepresentation;
import com.appeals.result.representations.Representation;
import com.appeals.result.representations.AppealUri;

public class CreateAppealActivity {
    public AppealRepresentation create(Appeal appeal, AppealUri requestUri) {
        appeal.setStatus(AppealStatus.GENERATING);
                
        Identifier identifier = AppealRepository.current().store(appeal);
        
        AppealUri appealUri = new AppealUri(requestUri.getBaseUri() + "/appeal/" + identifier.toString());
        AppealUri paymentUri = new AppealUri(requestUri.getBaseUri() + "/payment/" + identifier.toString());
        return new AppealRepresentation(appeal, 
                new Link(Representation.RELATIONS_URI + "cancel", appealUri), 
                new Link(Representation.RELATIONS_URI + "update", appealUri),
                new Link(Representation.SELF_REL_VALUE, appealUri));
    }
}
