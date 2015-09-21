package com.appeals.result.activities;

import com.appeals.result.model.Identifier;
import com.appeals.result.model.Appeal;
import com.appeals.result.model.AppealStatus;
import com.appeals.result.repositories.AppealRepository;
import com.appeals.result.representations.AppealRepresentation;

public class CompleteAppealActivity {

    public AppealRepresentation completeAppeal(Identifier id) {
        AppealRepository repository = AppealRepository.current();
        if (repository.has(id)) {
            Appeal appeal = repository.get(id);

            if (appeal.getStatus() == AppealStatus.READY) {
                appeal.setStatus(AppealStatus.DONE);
            } else if (appeal.getStatus() == AppealStatus.DONE) {
                throw new AppealAlreadyCompletedException();
            }

            return new AppealRepresentation(appeal);
        } else {
            throw new NoSuchAppealException();
        }
    }
}
