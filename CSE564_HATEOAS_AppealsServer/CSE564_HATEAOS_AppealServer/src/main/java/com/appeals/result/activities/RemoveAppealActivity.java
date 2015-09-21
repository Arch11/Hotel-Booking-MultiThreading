package com.appeals.result.activities;

import com.appeals.result.model.Identifier;
import com.appeals.result.model.Appeal;
import com.appeals.result.model.AppealStatus;
import com.appeals.result.repositories.AppealRepository;
import com.appeals.result.representations.AppealRepresentation;
import com.appeals.result.representations.AppealUri;

public class RemoveAppealActivity {
    public AppealRepresentation delete(AppealUri appealUri) {
        // Discover the URI of the appeal that has been cancelled
        
        Identifier identifier = appealUri.getId();

        AppealRepository appealRepository = AppealRepository.current();

        if (appealRepository.appealNotPlaced(identifier)) {
            throw new NoSuchAppealException();
        }

        Appeal appeal = appealRepository.get(identifier);

        // Can't delete a ready or preparing appeal
        if (appeal.getStatus() == AppealStatus.PROCESSING || appeal.getStatus() == AppealStatus.READY) {
            throw new AppealDeletionException();
        }

        if(appeal.getStatus() == AppealStatus.GENERATING) { // An unpaid appeal is being cancelled 
            appealRepository.remove(identifier);
        }

        return new AppealRepresentation(appeal);
    }

}
