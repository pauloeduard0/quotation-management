package com.inatel.quotationmanagement.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Configuration
public class Notification {

    private String disclaimer;
    private HttpStatus httpStatusCode;
    private String message;
}
