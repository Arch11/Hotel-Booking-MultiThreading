package com.appeals.result.activities;

import com.appeals.result.representations.AppealUri;

public class UriExchange {

    public static AppealUri paymentForAppeal(AppealUri appealUri) {
        checkForValidAppealUri(appealUri);
        return new AppealUri(appealUri.getBaseUri() + "/payment/" + appealUri.getId().toString());
    }
    
    public static AppealUri appealForPayment(AppealUri paymentUri) {
        checkForValidPaymentUri(paymentUri);
        return new AppealUri(paymentUri.getBaseUri() + "/appeal/" + paymentUri.getId().toString());
    }

    public static AppealUri receiptForPayment(AppealUri paymentUri) {
        checkForValidPaymentUri(paymentUri);
        return new AppealUri(paymentUri.getBaseUri() + "/receipt/" + paymentUri.getId().toString());
    }
    
    public static AppealUri appealForReceipt(AppealUri receiptUri) {
        checkForValidReceiptUri(receiptUri);
        return new AppealUri(receiptUri.getBaseUri() + "/appeal/" + receiptUri.getId().toString());
    }

    private static void checkForValidAppealUri(AppealUri appealUri) {
        if(!appealUri.toString().contains("/appeal/")) {
            throw new RuntimeException("Invalid Appeal URI");
        }
    }
    
    private static void checkForValidPaymentUri(AppealUri payment) {
        if(!payment.toString().contains("/payment/")) {
            throw new RuntimeException("Invalid Payment URI");
        }
    }
    
    private static void checkForValidReceiptUri(AppealUri receipt) {
        if(!receipt.toString().contains("/receipt/")) {
            throw new RuntimeException("Invalid Receipt URI");
        }
    }
}
