package com.appeals.result.activities;

import com.appeals.result.model.Identifier;
import com.appeals.result.model.Appeal;
import com.appeals.result.repositories.AppealRepository;
import com.appeals.result.representations.AppealRepresentation;
import com.appeals.result.representations.AppealUri;

public class ReadAppealActivity {
    public AppealRepresentation retrieveByUri(AppealUri appealUri) {
        Identifier identifier  = appealUri.getId();
        
        Appeal appeal = AppealRepository.current().get(identifier);
        
        if(appeal == null) {
            throw new NoSuchAppealException();
        }
        
        return AppealRepresentation.createResponseAppealRepresentation(appeal, appealUri);
    }
}
