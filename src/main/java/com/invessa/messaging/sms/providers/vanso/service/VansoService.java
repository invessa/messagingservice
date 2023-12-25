package com.invessa.messaging.sms.providers.vanso.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.invessa.messaging.repository.SMSMessagesRepository;
import com.invessa.messaging.model.SMSMessages;
import com.invessa.messaging.sms.providers.vanso.exception.VansoRuntimeException;
import com.invessa.messaging.sms.providers.vanso.properties.VansoProperties;
import com.invessa.messaging.sms.response.ErrorResponse;
import com.invessa.messaging.sms.providers.vanso.request.AccountDetailsRequest;
import com.invessa.messaging.sms.providers.vanso.request.BulkSMSRequest;
import com.invessa.messaging.sms.providers.vanso.request.DLRRequest;
import com.invessa.messaging.sms.providers.vanso.request.VansoSMSRequest;
import com.invessa.messaging.sms.providers.vanso.response.AccountDetailsResponse;
import com.invessa.messaging.sms.response.MessageResponse;
import com.invessa.messaging.sms.providers.vanso.response.SMSResponse;
import com.invessa.messaging.sms.providers.vanso.response.VansoErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;


@Service
@Slf4j
public class VansoService {

    @Autowired
    SMSMessagesRepository smsMessagesRepository;

    private final WebClient webClient = WebClient.create();
    private final String authString = "basic " + Base64.getEncoder().encodeToString(( VansoProperties.id+ ":" + VansoProperties.pwd).getBytes());
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String sendSMSEndpoint = VansoProperties.base_url+VansoProperties.sms_url;

    public ResponseEntity<?> sendSMS(VansoSMSRequest vansoSmsRequest) {
        log.info("Values {}", VansoProperties.id + ":" + VansoProperties.pwd);
        log.info("In VansoService || in sendSMS");
        log.info("sendSMSEndpoint >> {}", sendSMSEndpoint);
        log.info("Request message :: {}", gson.toJson(vansoSmsRequest));
        SMSResponse smsResponse;
        try {
            log.info("Calling Vanso API");
            smsResponse = webClient.post()
                    .uri(sendSMSEndpoint)
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, authString)
                    .body(Mono.just(vansoSmsRequest), VansoSMSRequest.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                            clientResponse.bodyToMono(VansoErrorResponse.class)
                                    .flatMap(
                                            error -> Mono.error(new VansoRuntimeException())))
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                            clientResponse.bodyToMono(VansoErrorResponse.class)
                                    .flatMap(
                                            error -> Mono.error(new RuntimeException(error.getErrorCode() + "#" + error.getErrorMessage() + "#" + "Unexpected Server Error"))))
                    .bodyToMono(SMSResponse.class)
                    .block();
        } catch (RuntimeException exception) {
            //throw new VansoRuntimeException("21","failed", re.getMessage());
            log.error("Error in Vanso API Call || Error:: {}", "Server error");
            log.error("ERROR:: "+exception.getMessage());
            Map<String, List<String>> errorInfo = new HashMap<>();
            List<String> errorList = new ArrayList<>();
            String[] errMsg = exception.getMessage().split("#");
            //errorList.add("errorCode: " + errMsg[0]);
            //errorList.add("errorMessage: " + errMsg[1]);
            errorList.add("error "+exception.getMessage());
            errorInfo.put("errors", errorList);
            return new ResponseEntity<>(new ErrorResponse("21", "failed", exception.getMessage(), errorInfo), HttpStatus.BAD_REQUEST);
            //return new ErrorResponse("21","failed", errMsg[2], errorInfo);
        }
        log.info("Calling Vanso API completed");
        log.info("Response message :: {}",gson.toJson(smsResponse));
        return new ResponseEntity<>(new MessageResponse("00","success","SMS Sent",smsResponse), HttpStatus.OK);
    }

    public boolean sendSMSMessage(VansoSMSRequest vansoSmsRequest) {
        log.info("Values {}", VansoProperties.id + ":" + VansoProperties.pwd);
        log.info("In VansoService || in sendSMSMessage");
        log.info("sendSMSEndpoint >> {}", VansoProperties.base_url+VansoProperties.sms_url);
        log.info("Request message :: {}", gson.toJson(vansoSmsRequest));
        String smsEndPoint = VansoProperties.base_url+VansoProperties.sms_url;
        String vendorAuth = "basic " + Base64.getEncoder().encodeToString(( VansoProperties.id+ ":" + VansoProperties.pwd).getBytes());
        SMSResponse smsResponse = new SMSResponse();

        boolean callResponse =  true;
        try {
            log.info("Calling Vanso API");
            smsResponse = webClient.post()
                    .uri(smsEndPoint)
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, vendorAuth)
                    .body(Mono.just(vansoSmsRequest), VansoSMSRequest.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                            clientResponse.bodyToMono(VansoErrorResponse.class)
                                    .flatMap(
                                            error -> Mono.error(new VansoRuntimeException())))
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                            clientResponse.bodyToMono(VansoErrorResponse.class)
                                    .flatMap(
                                            error -> Mono.error(new RuntimeException(error.getErrorCode() + "#" + error.getErrorMessage() + "#" + "Unexpected Server Error"))))
                    .bodyToMono(SMSResponse.class)
                    .block();
        } catch (RuntimeException exception) {
            //throw new VansoRuntimeException("21","failed", re.getMessage());
            log.error("Error in Vanso API Call || Error:: Server error");
            log.error("Exception Message || Error:: {}", exception.getMessage());
            Map<String, List<String>> errorInfo = new HashMap<>();
            List<String> errorList = new ArrayList<>();
            if(!Objects.isNull(exception.getMessage())){
                String[] errMsg = exception.getMessage().split("#");
                errorList.add("errorCode: " + errMsg[0]);
                errorList.add("errorMessage: " + errMsg[1]);
                errorInfo.put("errors", errorList);
            }
            log.error("Error in Vanso API Call || Error:: {}", errorInfo.toString());
            callResponse =  false;
        }
        //log.info("Calling Vanso API completed");
        log.info("Response message :: {}",gson.toJson(smsResponse));
        SMSMessages smsMessages = new SMSMessages();
        if(!Objects.isNull(smsResponse)){
            smsMessages.setErrorMessage(smsResponse.getErrorMessage());
            smsMessages.setDestination(smsResponse.getDestination());
            smsMessages.setStatus(smsResponse.getStatus());
            smsMessages.setErrorCode(smsResponse.getErrorCode());
            smsMessagesRepository.save(smsMessages);
        }

        return callResponse;

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

}
