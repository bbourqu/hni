package org.hni.order.dao;

import org.hni.order.om.Order;
import org.hni.order.service.OrderTestData;
import org.hni.provider.om.ProviderLocation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Verifies that the dao is making successful queries
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-applicationContext.xml"} )
@Transactional
public class TestDefaultOrderDAO {

    @Inject
    private OrderDAO orderDao;

    @Test
    public void testGetByProvider() {
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < 5; i ++) {
            orderDao.save(OrderTestData.getTestOrder(now.minus(i, ChronoUnit.MINUTES), new ProviderLocation(1L)));
            orderDao.save(OrderTestData.getTestOrder(now.minus(i, ChronoUnit.MINUTES), new ProviderLocation(2L)));
            orderDao.save(OrderTestData.getTestOrder(now.minus(i, ChronoUnit.MINUTES), new ProviderLocation(3L)));
        }

        List<Order> orders = (ArrayList<Order>) orderDao.get(new ProviderLocation(2L), LocalDateTime.now().minus(1, ChronoUnit.DAYS), LocalDateTime.now());

        //Verifies that the correct list of orders was returned
        Assert.assertEquals(5, orders.size());
        for (Order order : orders) {
            Assert.assertEquals(new Long(2L), order.getProviderLocation().getId());
        }

        //Verifies that orders were not returned when search parameters were bad
        orders = (ArrayList<Order>) orderDao.get(new ProviderLocation(9L), LocalDateTime.now().minus(1, ChronoUnit.DAYS), LocalDateTime.now());
        Assert.assertEquals(0, orders.size());
    }

}
