package org.hni.order.dao;

import org.hni.common.dao.BaseDAO;
import org.hni.order.om.Order;
import org.hni.provider.om.ProviderLocation;
import org.hni.user.om.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public interface OrderDAO extends BaseDAO<Order> {

	Collection<Order> get(User user, LocalDate fromDate, LocalDate toDate);

	Collection<Order> get(ProviderLocation providerLocation, LocalDateTime fromDate, LocalDateTime toDate);

}
