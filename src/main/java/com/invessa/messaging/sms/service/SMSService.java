package com.invessa.messaging.sms.service;

import com.invessa.messaging.sms.providers.vanso.dto.SMSData;
import com.invessa.messaging.sms.request.SMSRequest;
import com.invessa.messaging.sms.response.ErrorResponse;
import com.invessa.messaging.sms.providers.vanso.request.VansoSMSRequest;
import com.invessa.messaging.sms.providers.vanso.service.VansoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SMSService {


    private static String smsProvider;
    private static String vansoSource;

    @Value("${invessa.sms.provider}")
    private void setSmsProvider(String vSmsProvider){
        smsProvider = vSmsProvider;
    }

    @Value("${invessa.vanso.src}")
    private void setVansoSource(String vVansoSource){vansoSource = vVansoSource;}

    @Autowired
    VansoService vansoService;

    public ResponseEntity<?> sendSMS(SMSRequest smsRequest){
        log.info("In SMSService | sendSMSMessage");
        log.info("SMS Provider >> {}",smsProvider);
        if (smsProvider.equals("vanso")) {
            log.info("In Vanso call");
            VansoSMSRequest vansoSMSRequest = new VansoSMSRequest();
            SMSData smsData = SMSData.builder()
                            .dest(smsRequest.getPhone_number())
                            .src(vansoSource)
                            .text(smsRequest.getSms_text())
                            .build();
            vansoSMSRequest.setSms(smsData);
            //return VansoService.sendSMS(vansoSMSRequest);
            return vansoService.sendSMS(vansoSMSRequest);
        }else{
            log.error("Error in sending SMS || Error::unknown SMS Provider ");
            Map<String, List<String>> errorInfo = new HashMap<>();
            return new ResponseEntity<>(new ErrorResponse("22","failed","Unknown SMS Provider = " + smsProvider,errorInfo),HttpStatus.BAD_REQUEST);
        }

    }
    public boolean sendSMSMessage(SMSRequest smsRequest){
        log.info("In SMSService | sendSMSMessage");
        log.info("SMS Provider >> {}",smsProvider);
        if (smsProvider.equals("vanso")) {
            log.info("In Vanso call");
            VansoSMSRequest vansoSMSRequest = new VansoSMSRequest();
            SMSData smsData = SMSData.builder()
                    .dest(smsRequest.getPhone_number())
                    .src(vansoSource)
                    .text(smsRequest.getSms_text())
                    .build();
            vansoSMSRequest.setSms(smsData);
            //return VansoService.sendSMSMessage(vansoSMSRequest);
            return vansoService.sendSMSMessage(vansoSMSRequest);
        }else{
            log.error("Error in sending SMS || Error::unknown SMS Provider ");
            return false;
        }

    }
}