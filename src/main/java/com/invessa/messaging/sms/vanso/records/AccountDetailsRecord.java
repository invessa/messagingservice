package com.invessa.messaging.sms.vanso.records;

import java.util.List;

public record AccountDetailsRecord(int balance, int creditLine, String currency,
                                   String dlrCallbackUrl, boolean dlrEnabled, List<String> ipWhitelist,
                                   String moCallbackUrl) {

}
