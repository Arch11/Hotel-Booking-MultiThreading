package com.appeals.result.activities;

import com.appeals.result.representations.AppealUri;

public class UriExchange {

    private static void checkForValidAppealUri(AppealUri appealUri) {
        if(!appealUri.toString().contains("/appeal/")) {
            throw new RuntimeException("Invalid Appeal URI");
        }
    }
    
}
