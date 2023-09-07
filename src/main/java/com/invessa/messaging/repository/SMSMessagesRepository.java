package com.invessa.messaging.repository;

import com.invessa.messaging.model.SMSMessages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SMSMessagesRepository extends JpaRepository<SMSMessages,Long> {

}
