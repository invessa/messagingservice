package com.invessa.messaging.sms.service;

import com.invessa.messaging.sms.request.SMSRequest;
import com.invessa.messaging.sms.response.ErrorResponse;
import com.invessa.messaging.sms.vanso.request.VansoSMSRequest;
import com.invessa.messaging.sms.response.MessageResponse;
import com.invessa.messaging.sms.vanso.service.VansoService;
import lombok.extern.slf4j.Slf4j;
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


    static String smsProvider;

    @Value("${invessa.sms.provider}")
    private void setSmsProvider(String vSmsProvider){
        smsProvider = vSmsProvider;
    }

    public static ResponseEntity<?> sendSMS(SMSRequest smsRequest){
        log.info("In SMSService | sendSMS");
        log.info("SMS Provider >> {}",smsProvider);
        if (smsProvider.equals("vanso")) {
            log.info("In Vanso call");
            VansoSMSRequest vansoSMSRequest = new VansoSMSRequest();
            vansoSMSRequest.setDest(smsRequest.getPhoneNumber());
            vansoSMSRequest.setText(smsRequest.getSmsText());
            vansoSMSRequest.setSrc("Invessa");
            //MessageResponse messageResponse = VansoService.sendSMS(vansoSMSRequest);
            //return new ResponseEntity<>(messageResponse,HttpStatus.OK);
            return VansoService.sendSMS(vansoSMSRequest);
        }else{
            log.error("Error in sending SMS || Error::unknown SMS Provider ");
            Map<String, List<String>> errorInfo = new HashMap<>();
            return new ResponseEntity<>(new ErrorResponse("22","failed","Unknown SMS Provider = " + smsProvider,errorInfo),HttpStatus.BAD_REQUEST);
        }


    }
}