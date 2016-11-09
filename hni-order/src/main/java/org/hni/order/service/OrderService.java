package org.hni.order.service;

import org.hni.common.service.BaseService;
import org.hni.order.om.Order;
import org.hni.provider.om.ProviderLocation;
import org.hni.user.om.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

public interface OrderService extends BaseService<Order> {

	/**
	 * Returns a collection of orders for the user created on the date.
	 * @param user
	 * @param startDate
	 * @return
	 */
	Collection<Order> get(User user, LocalDate startDate);
	
	/**
	 * Returns a collection of orders for the user between 2 dates
	 * @param user
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Collection<Order> get(User user, LocalDate startDate, LocalDate endDate);
	
	/**
	 * Locks an order.  This is to prevent the system from giving the same order to another
	 * person to process.
	 * @param order
	 * @return
	 */
	Order lock(Order order);
	
	/**
	 * Releases the lock on an order.
	 * @param order
	 * @return
	 */
	Order release(Order order);
	
	/**
	 * Gets the next available order to process for a particular provider
	 * where the order was created on a date.
	 * @param providerLocation
	 * @param orderDate
	 * @return
	 */
	Order next(ProviderLocation providerLocation, LocalDateTime orderDate);
	
	/**
	 * Returns the next available order for a provider
	 * @param providerLocation
	 * @return
	 */
	Order next(ProviderLocation providerLocation);
	
	/**
	 * Sets an order to completed.
	 * @param order
	 * @return
	 */
	Order complete(Order order);
}
