package org.hni.order.service;

import org.apache.log4j.BasicConfigurator;
import org.hni.common.DateUtils;
import org.hni.order.om.Order;
import org.hni.provider.om.ProviderLocation;
import org.hni.user.om.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-applicationContext.xml"} )
@Transactional
public class TestOrderService {
	
	@Inject
	private OrderService orderService;
	
	public TestOrderService() {
		BasicConfigurator.configure();
	}

	@Test
	public void testAddOrder() {
		Order order = OrderTestData.getTestOrder();

		orderService.save(order);
		
		Order order2 = orderService.get(order.getId());

		assertEquals(2, order2.getOrderItems().size());
	}
	
	@Test
	public void testGetOrdersSince() {
		User user = new User(2L);
		LocalDate startDate = LocalDate.now().minusDays(3);
		Collection<Order> orders = orderService.get(user, startDate);
		assertEquals(1, orders.size());
	}

	@Test
	public void testOrderComplete() {
		Date pickupDate = new Date();

		Order order = OrderTestData.getTestOrder(LocalDateTime.now() ,new ProviderLocation(99L));
		// Checks that the pickup date is null prior to the completion methods
		assertEquals(null, order.getPickupDate());

		Order returnOrder = orderService.complete(order);
		// Verifies that the pickup date has been updated
		assertTrue(pickupDate.before(returnOrder.getPickupDate())
				|| pickupDate.getTime() == returnOrder.getPickupDate().getTime());

		//Checks that it was properly loaded into database
		Order orderFromDatabase = orderService.get(order.getId());
		assertNotNull(orderFromDatabase.getPickupDate());
		assertTrue(pickupDate.before(orderFromDatabase.getPickupDate())
				|| pickupDate.getTime() == orderFromDatabase.getPickupDate().getTime());
	}

	@Test
	public void testNextOrder_Success() {
		LocalDateTime fromDate = LocalDateTime.now().minus(12, ChronoUnit.HOURS);

		//Loads 10 orders into database with two different providers and orders offset by 1 hour
		for (int i = 0; i < 5; i ++) {
			orderService.save(OrderTestData.getTestOrder(fromDate.minus(i, ChronoUnit.MINUTES), new ProviderLocation(1L)));
			orderService.save(OrderTestData.getTestOrder(fromDate.minus(i, ChronoUnit.MINUTES), new ProviderLocation(2L)));
		}

		//Gets the next order, checks its time/date is good, marks it complete, and repeats.
		for (int i = 5; i > 0; i --) {
			Order order = orderService.next(new ProviderLocation(1L));

			assertEquals(DateUtils.asDate(fromDate.minus(i - 1, ChronoUnit.MINUTES)), order.getOrderDate());
			assertNull(order.getPickupDate());
			assertEquals(new Long(1L) , order.getProviderLocation().getId());
			orderService.complete(order);
		}

	}

	@Test
	public void testNextOrder_InvalidDate() {
		//Places orders 12 hours into the future
		LocalDateTime fromDate = LocalDateTime.now().plus(12, ChronoUnit.HOURS);

		//Loads 10 orders into database with two different providers and orders offset by 1 hour
		for (int i = 0; i < 5; i ++) {
			orderService.save(OrderTestData.getTestOrder(fromDate.minus(i, ChronoUnit.MINUTES), new ProviderLocation(10L)));
			orderService.save(OrderTestData.getTestOrder(fromDate.minus(i, ChronoUnit.MINUTES), new ProviderLocation(20L)));
		}

		//Search from tomorrow to today (backwards)
		Order order = orderService.next(new ProviderLocation(10L), LocalDateTime.now().plus(1, ChronoUnit.DAYS));
		//No object should be returned for searches in the future
		assertNull(order);
	}

}
