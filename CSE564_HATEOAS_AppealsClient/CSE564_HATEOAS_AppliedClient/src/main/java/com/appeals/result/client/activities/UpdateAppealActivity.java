package com.appeals.result.client.activities;

import java.net.URI;

import com.appeals.result.client.ClientAppeal;
import com.appeals.result.model.Appeal;
import com.appeals.result.representations.AppealRepresentation;

public class UpdateAppealActivity extends Activity {

    private final URI updateUri;
    private AppealRepresentation updatedAppealRepresentation;

    public UpdateAppealActivity(URI updateUri) {
        this.updateUri = updateUri;
    }

    public void updateAppeal(Appeal appeal) {
        try {
            updatedAppealRepresentation = binding.updateAppeal(appeal, updateUri);
            actions = new RepresentationHypermediaProcessor().extractNextActionsFromAppealRepresentation(updatedAppealRepresentation);
        } catch (MalformedAppealException e) {
            actions = retryCurrentActivity();
        } catch (ServiceFailureException e) {
            actions = retryCurrentActivity();
        } catch (NotFoundException e) {
            actions = noFurtherActivities();
        } catch (CannotUpdateAppealException e) {
            actions = noFurtherActivities();
        }
    }
    
    public ClientAppeal getAppeal() {
        return new ClientAppeal(updatedAppealRepresentation.getAppeal());
    }
}
