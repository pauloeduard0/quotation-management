package com.inatel.quotationmanagement.listener;

import com.inatel.quotationmanagement.adapter.StockAdapter;
import com.inatel.quotationmanagement.model.rest.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;


@Slf4j
@Component
public class NotificationListener implements ApplicationListener<ApplicationReadyEvent> {

    private final StockAdapter stockAdapter;

    public NotificationListener(StockAdapter stockAdapter) {
        this.stockAdapter = stockAdapter;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Notification[] notificationList = stockAdapter.registerOnStockManager();

        String response = Stream.of(notificationList).map(notification -> String.format("%s:%s", notification.host(), notification.port()))
                .reduce((n1, n2) -> String.format("%s,%s", n1, n2))
                .orElse("");
        log.info("Response: {}", String.format("[%s]", response));
    }

}