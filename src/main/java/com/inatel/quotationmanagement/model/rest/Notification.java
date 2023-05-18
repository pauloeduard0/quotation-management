package com.inatel.quotationmanagement.model.rest;

import lombok.Builder;

@Builder
public record Notification(String host, String port) {

}