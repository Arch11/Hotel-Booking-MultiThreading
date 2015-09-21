package com.appeals.result.client.activities;

import com.appeals.result.representations.AppealRepresentation;

class RepresentationHypermediaProcessor {

    Actions extractNextActionsFromAppealRepresentation(AppealRepresentation representation) {
        Actions actions = new Actions();

        if (representation != null) {

            if (representation.getUpdateLink() != null) {
                actions.add(new UpdateAppealActivity(representation.getUpdateLink().getUri()));
            }

            if (representation.getSelfLink() != null) {
                actions.add(new ReadAppealActivity(representation.getSelfLink().getUri()));
            }

            if (representation.getCancelLink() != null) {
                actions.add(new CancelAppealActivity(representation.getCancelLink().getUri()));
            }
        }

        return actions;
    }


}
