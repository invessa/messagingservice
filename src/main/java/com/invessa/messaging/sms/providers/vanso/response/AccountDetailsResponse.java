package com.invessa.messaging.sms.providers.vanso.response;

import lombok.Data;

import java.util.List;

@Data
public class AccountDetailsResponse {
    int balance;
    int creditLine;
    String currency;
    String dlrCallbackUrl;
    boolean dlrEnabled;
    List<String> ipWhitelist;
    String moCallbackUrl;

}
