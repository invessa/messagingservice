package com.invessa.messaging.sms.providers.vanso.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;


@Configuration
public class VansoProperties {

    public static String id;
    public static String pwd;
    public static String base_url;

    public static String sms_url;

    public static String dlr_url;

    public static String account_info_url;


    public static String bulk_sms;

    @Value("${invessa.vanso.systemid}")
    public void setId(String varId){
        id = varId;
    }

    @Value("${invessa.vanso.password}")
    public void setPwd(String varPwd){
        pwd = varPwd;
    }

    @Value("${invessa.vanso.base_url}")
    public void setBase_url(String baseUrl){
        base_url = baseUrl;
    }

    @Value("${invessa.vanso.api.url.sms}")
    public void setSms_url(String smsUrl){
        sms_url = smsUrl;
    }

    @Value("${invessa.vanso.api.url.dlr}")
    public void setDlr_url(String dlrUrl){
        dlr_url = dlrUrl;
    }

    @Value("${invessa.vanso.api.url.account_info}")
    public void setAccount_info_url(String accountInfoUrl){
        account_info_url = accountInfoUrl;
    }

    @Value("${invessa.vanso.api.url.bulk_sms}")
    public void setBulk_sms(String bulkSms){
        bulk_sms = bulkSms;
    }

}
