package org.hni.order.service;

import org.hni.common.DateUtils;
import org.hni.order.om.Order;
import org.hni.order.om.OrderItem;
import org.hni.provider.om.MenuItem;
import org.hni.provider.om.ProviderLocation;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Test data to be used for TestOrderService
 */
public class OrderTestData {

    public static Order getTestOrder() {
        return getTestOrder(LocalDateTime.now(), new ProviderLocation(1L));
    }

    public static Order getTestOrder(LocalDateTime date, ProviderLocation providerLocation) {
        Order order = new Order();

        order.setCreatedById(1L);
        order.setOrderDate(DateUtils.asDate(date));
        order.setProviderLocation(providerLocation);
        order.getOrderItems().add(new OrderItem(1L, 8.99, new MenuItem(1L)));
        order.getOrderItems().add(new OrderItem(2L, 7.99, new MenuItem(2L)));

        return order;
    }

}
