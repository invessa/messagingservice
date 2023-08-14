package com.invessa.messaging.sms.vanso.exception;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class VansoRuntimeException extends RuntimeException{

    private String code;
    private String status;
    private String message;
    private Object data;

    public VansoRuntimeException(String code, String status, String message){
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public VansoRuntimeException(String message){
        this.message = message;
    }

}
