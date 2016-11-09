package org.hni.order.service;

import org.hni.common.service.AbstractService;
import org.hni.order.dao.OrderDAO;
import org.hni.order.om.Order;
import org.hni.provider.om.ProviderLocation;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultOrderService extends AbstractService<Order> implements OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	private OrderDAO orderDao;
	
	@Inject
	public DefaultOrderService(OrderDAO orderDao) {
		super(orderDao);
		this.orderDao = orderDao;
	}

	@Override
	public Collection<Order> get(User user, LocalDate startDate) {
		return get(user, startDate, LocalDate.now());
	}

	@Override
	public Collection<Order> get(User user, LocalDate startDate, LocalDate endDate) {
		return this.orderDao.get(user,  startDate, endDate);
	}

	@Override
	public Order lock(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order release(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order next(ProviderLocation providerLocation) {
		// Returns an uncompleted order placed within the last 24 hours. Null if none found.
		return next(providerLocation, LocalDateTime.now().minus(1, ChronoUnit.DAYS));
	}

	@Override
	public Order next(ProviderLocation providerLocation, LocalDateTime fromDate) {
		// Returns an uncompleted order placed with the provided time window.  Null if none found.
		List<Order> orders = (ArrayList) orderDao.get(providerLocation, fromDate, LocalDateTime.now());
		return orders.stream()
				.filter(order -> order.getPickupDate() == null)
				.sorted((order1, order2) -> Long.compare(order1.getOrderDate().getTime(), order2.getOrderDate().getTime()))
				.findFirst()
				.orElse(null);
	}

	@Override
	public Order complete(Order order) {
		order.setPickupDate(new Date());
		save(order);
		return order;
	}

}
