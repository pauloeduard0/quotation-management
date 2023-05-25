package com.inatel.quotationmanagement.adapter;

import com.inatel.quotationmanagement.exception.StockManagerConnectionException;
import com.inatel.quotationmanagement.model.rest.Notification;
import com.inatel.quotationmanagement.model.rest.Stock;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.Arrays;
import java.util.List;

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

    private final String stockManagerBaseUrl;
    private final WebClient webClient;

    public StockAdapter(
            @Value("${server.manager.host}") String stockManagerHost,
            @Value("${server.manager.port}") String stockManagerPort
    ) {
        this.stockManagerBaseUrl = String.format("http://%s:%s", stockManagerHost, stockManagerPort);
        this.webClient = WebClient.builder()
                .baseUrl(this.stockManagerBaseUrl)
                .build();
    }

    @Cacheable("stock")
    public List<Stock> getAllStock() {
        try {
            Stock[] stock = this.webClient.get().uri("/stock").retrieve().bodyToMono(Stock[].class).block();
            return Arrays.asList(stock);
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