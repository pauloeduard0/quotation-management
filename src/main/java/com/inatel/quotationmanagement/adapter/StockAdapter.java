package com.inatel.quotationmanagement.adapter;

import com.inatel.quotationmanagement.exception.StockManagerConnectionException;
import com.inatel.quotationmanagement.model.rest.Notification;
import com.inatel.quotationmanagement.model.rest.Stock;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.Arrays;
import java.util.List;

@EnableCaching
@Service
@Slf4j
public class StockAdapter {

    @Value("${server.host}")
    private String serverHost;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.manager.host}")
    private String stockManagerHost;

    @Value("${server.manager.port}")
    private String stockManagerPort;

    private String stockManagerBaseUrl;
    private WebClient webClient;

    @PostConstruct
    public void buildStockManagerBaseUrl() {
        this.stockManagerBaseUrl = String.format("http://%s:%s", this.stockManagerHost, this.stockManagerPort);
        this.webClient = WebClient.builder()
                .baseUrl(this.stockManagerBaseUrl)
                .build();
    }

    @Cacheable("stock")
    public List<Stock> getAllStock() {
        try {
            Stock[] stockArr = this.webClient.get().uri("/stock").retrieve().bodyToMono(Stock[].class).block();
            return Arrays.asList(stockArr);
        } catch (WebClientException webClientException) {
            throw new StockManagerConnectionException(this.stockManagerBaseUrl);
        }
    }

    @CacheEvict("stock")
    public void clearStockCache() {
        log.info("Cache cleared");
    }

    public Notification[] registerOnStockManager() {
        try {
            log.info("Registering at {}", this.stockManagerBaseUrl);
            Notification notification = Notification.builder()
                    .host(this.serverHost)
                    .port(this.serverPort)
                    .build();

            webClient = WebClient.builder().baseUrl("http://" + this.stockManagerHost + ":" + this.stockManagerPort).build();

            return webClient.post()
                    .uri("/notification")
                    .body(BodyInserters.fromValue(notification))
                    .retrieve()
                    .bodyToMono(Notification[].class)
                    .block();
        } catch (WebClientException webClientException) {
            webClientException.printStackTrace();
            throw new StockManagerConnectionException(this.stockManagerBaseUrl);
        }
    }


}
