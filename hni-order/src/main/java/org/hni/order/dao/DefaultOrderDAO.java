package org.hni.order.dao;

import org.hni.common.DateUtils;
import org.hni.common.dao.AbstractDAO;
import org.hni.order.om.Order;
import org.hni.provider.om.ProviderLocation;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Component
public class DefaultOrderDAO extends AbstractDAO<Order> implements OrderDAO {
	private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);
	
	public DefaultOrderDAO() {
		super(Order.class);
	}

	@Override
	public Collection<Order> get(User user, LocalDate fromDate, LocalDate toDate) {
		try {
			Query q = em.createQuery("SELECT x FROM Order x WHERE x.userId = :userId AND x.orderDate BETWEEN :fromDate AND :toDate")
				.setParameter("userId", user.getId())
				.setParameter("fromDate", DateUtils.asDate(fromDate))
				.setParameter("toDate", DateUtils.asDate(toDate));
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<Order> get(ProviderLocation providerLocation, LocalDateTime fromDate, LocalDateTime toDate) {
		try {
			Query q = em.createQuery("SELECT x FROM Order x "
					+ "WHERE x.orderDate BETWEEN :fromDate AND :toDate "
					+ "AND x.providerLocation = :providerLocationId ")
					.setParameter("providerLocationId", providerLocation)
					.setParameter("fromDate", DateUtils.asDate(fromDate))
					.setParameter("toDate", DateUtils.asDate(toDate));
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

}
