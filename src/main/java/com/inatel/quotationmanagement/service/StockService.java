//package com.inatel.quotationmanagement.service;
//
//import com.inatel.quotationmanagement.exception.StockManagerConnectionException;
//import com.inatel.quotationmanagement.model.rest.Stock;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.Cacheable;
//import lombok.Value;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.client.WebClientException;
//
//import javax.management.Notification;
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//@Slf4j
//public class StockService {
//
//    @Value("${server.host}")
//    private String serverHost;
//
//    @Value("${server.port}")
//    private String serverPort;
//
//    @Value("${server.manager.host}")
//    private String stockManagerHost;
//
//    @Value("${server.manager.port}")
//    private String stockManagerPort;
//
//    private String stockManagerBaseUrl;
//    private WebClient webClient;
//
//    @PostConstruct
//    public void buildStockManagerBaseUrl()
//    {
//        this.stockManagerBaseUrl = String.format("http://%s:%s", this.stockManagerHost, this.stockManagerPort);
//        this.webClient = WebClient.builder()
//                .baseUrl(this.stockManagerBaseUrl)
//                .build();
//    }
//
//    @Cacheable(cacheNames = "stock")
//    public List<Stock> getAllStock()
//    {
//        try
//        {
//            Stock[] stockArr = this.webClient.get().uri("/stock").retrieve().bodyToMono(Stock[].class).block();
//            return Arrays.asList(stockArr);
//        }
//        catch (WebClientException webClientException)
//        {
//            throw new StockManagerConnectionException(this.stockManagerBaseUrl);
//        }
//    }
//
//    @CacheEvict(cacheNames = "stock")
//    public void clearStockCache()
//    {
//        log.info("Cache cleared");
//    }
//
//    public Notification[] registerOnStockManager()
//    {
//        try
//        {
//            log.info("Registering at {}", this.stockManagerBaseUrl);
//            Notification notification = Notification.builder()
//                    .host(this.serverHost)
//                    .port(this.serverPort)
//                    .build();
//
//            return this.webClient.port()
//                    .uri("/notification")
//                    .body(BodyInserts.fromValue(notification))
//                    .retrieve()
//                    .bodyToMono(Notification[].class)
//                    .block();
//        }
//        catch (WebClientException webClientException)
//        {
//            webClientException.printStackTrace();
//            throw new StockManagerConnectionException(this.stockManagerBaseUrl);
//        }
//    }
//
//
//}
