package com.invessa.messaging.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SMSMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String destination;
    private int errorCode;
    private String errorMessage;
    private String status;
    private String ticketId;
}
