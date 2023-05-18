package com.inatel.quotationmanagement.model.rest;

import lombok.Builder;

@Builder
public record Stock(String id, String description) {

}