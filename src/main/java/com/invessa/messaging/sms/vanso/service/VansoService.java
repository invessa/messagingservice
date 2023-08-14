package com.invessa.messaging.sms.vanso.service;

import com.invessa.messaging.sms.response.ErrorResponse;
import com.invessa.messaging.sms.vanso.exception.VansoRuntimeException;
import com.invessa.messaging.sms.vanso.properties.VansoProperties;
import com.invessa.messaging.sms.vanso.request.AccountDetailsRequest;
import com.invessa.messaging.sms.vanso.request.BulkSMSRequest;
import com.invessa.messaging.sms.vanso.request.DLRRequest;
import com.invessa.messaging.sms.vanso.request.VansoSMSRequest;
import com.invessa.messaging.sms.vanso.response.AccountDetailsResponse;
import com.invessa.messaging.sms.response.MessageResponse;
import com.invessa.messaging.sms.vanso.response.SMSResponse;
import com.invessa.messaging.sms.vanso.response.VansoErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;


@Service
@Slf4j
public class VansoService {

    static WebClient webClient = WebClient.create();
    static String authString = "basic " + Base64.getEncoder().encodeToString(( VansoProperties.id+ ":" + VansoProperties.pwd).getBytes());

    public static ResponseEntity<?> sendSMS(VansoSMSRequest vansoSmsRequest){
        String sendSMSEndpoint = VansoProperties.base_url+VansoProperties.sms_url;
        log.info("Values {}", VansoProperties.id+ ":" + VansoProperties.pwd);
        log.info("In VansoService || in sendSMS");
        log.info("sendSMSEndpoint >> {}",sendSMSEndpoint);
        SMSResponse smsResponse;
        try {
            log.info("Calling Vanso API");
             smsResponse = webClient.post()
                    .uri(sendSMSEndpoint)
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, authString)
                    .body(Mono.just(vansoSmsRequest), VansoSMSRequest.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,clientResponse ->
                            clientResponse.bodyToMono(VansoErrorResponse.class)
                                    .flatMap(
                            error -> Mono.error(new VansoRuntimeException())))
                    .onStatus(HttpStatusCode::is5xxServerError,clientResponse ->
                            clientResponse.bodyToMono(VansoErrorResponse.class)
                                    .flatMap(
                            error -> Mono.error(new RuntimeException(error.getErrorCode()+"#"+error.getErrorMessage()+"#"+"Unexpected Server Error"))))
                    .bodyToMono(SMSResponse.class)
                    .block();
        }catch (RuntimeException exception){
            //throw new VansoRuntimeException("21","failed", re.getMessage());
            log.error("Error in Vanso API Call || Error:: {}", "Server error");
            Map<String, List<String>> errorInfo = new HashMap<>();
            List<String> errorList = new ArrayList<>();
            String[] errMsg = exception.getMessage().split("#");
            errorList.add("errorCode: "+errMsg[0]);
            errorList.add("errorMessage: "+errMsg[1]);
            errorInfo.put("errors",errorList);
            return new ResponseEntity<>(new ErrorResponse("21","failed", errMsg[2], errorInfo),HttpStatus.BAD_REQUEST);
        }
        //return new MessageResponse("00","success","SMS Sent",smsResponse);
        log.info("Calling Vanso API completed");
        return new ResponseEntity<>(new MessageResponse("00","success","SMS Sent",smsResponse), HttpStatus.OK);
    }

    public void sendBulkSMS(BulkSMSRequest bulkSMSRequest){
        String sendSMSEndpoint = VansoProperties.base_url+VansoProperties.bulk_sms;
        SMSResponse smsResponse =  webClient.post()
                .uri(sendSMSEndpoint)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,authString)
                .body(Mono.just(bulkSMSRequest), BulkSMSRequest.class)
                .retrieve()
                .bodyToMono(SMSResponse.class)
                .block();
    }

    public void getAccountInfo(AccountDetailsRequest accountDetailsRequest){
        String accountInfoEndpoint = VansoProperties.base_url+VansoProperties.account_info_url;
        AccountDetailsResponse accountDetailsResponse =  webClient.get()
                .uri(accountInfoEndpoint)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,authString)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(AccountDetailsResponse.class)
                .block();
    }

    public void getDLR(DLRRequest dlrRequest){
        //UriBuilder uriBuilder;
        String accountInfoEndpoint = VansoProperties.base_url+VansoProperties.dlr_url;
        AccountDetailsResponse accountDetailsResponse =  webClient.get()
                .uri(uriBuilder->uriBuilder.path(accountInfoEndpoint).queryParam("ticketId",dlrRequest.getTicketId()).build())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,authString)
                .retrieve()
                .bodyToMono(AccountDetailsResponse.class)
                .block();
    }


}
