package com.inatel.quotationmanagement.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Configuration
public class Notification {

    private String host;
    private String port;


}
