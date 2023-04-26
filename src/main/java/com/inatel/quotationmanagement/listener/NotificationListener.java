package com.inatel.quotationmanagement.listener;

//import com.inatel.quotationmanagement.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.management.Notification;
import java.util.stream.Stream;


@Slf4j
@Component
public class NotificationListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

    }
    /*@Autowired
    private StockService stockService;

   *//* @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Notification[] notificationList = stockService.registerOnStockManager();

        String response = Stream.of(notificationList).map(notification -> String.format("%s:%s", notification.getHost(), notification,getPort()))
                .reduce((n1, n2) -> String.format("%s,%s", n1, n2))
                .orElse("");
        log.info("Response: {}", String.format("[%s]", response));
    }*/

}
