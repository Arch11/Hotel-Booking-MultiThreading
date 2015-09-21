package com.appeals.result.activities;

import com.appeals.result.model.Identifier;
import com.appeals.result.model.Appeal;
import com.appeals.result.model.AppealStatus;
import com.appeals.result.repositories.AppealRepository;
import com.appeals.result.representations.AppealRepresentation;
import com.appeals.result.representations.AppealUri;

public class UpdateAppealActivity {
    public AppealRepresentation update(Appeal appeal, AppealUri appealUri) {
        Identifier appealIdentifier = appealUri.getId();

        AppealRepository repository = AppealRepository.current();
        if (AppealRepository.current().appealNotPlaced(appealIdentifier)) { // Defensive check to see if we have the appeal
            throw new NoSuchAppealException();
        }

        if (!appealCanBeChanged(appealIdentifier)) {
            throw new UpdateException();
        }

        Appeal storedAppeal = repository.get(appealIdentifier);
        
        storedAppeal.setStatus(storedAppeal.getStatus());


        return AppealRepresentation.createResponseAppealRepresentation(storedAppeal, appealUri); 
    }
    
    private boolean appealCanBeChanged(Identifier identifier) {
        return AppealRepository.current().get(identifier).getStatus() == AppealStatus.GENERATING;
    }
}
