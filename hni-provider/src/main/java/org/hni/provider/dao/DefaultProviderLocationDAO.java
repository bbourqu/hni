package org.hni.provider.dao;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.springframework.stereotype.Component;

@Component
public class DefaultProviderLocationDAO extends AbstractDAO<ProviderLocation> implements ProviderLocationDAO {

	protected DefaultProviderLocationDAO() {
		super(ProviderLocation.class);
	}

	@Override
	public Collection<ProviderLocation> with(Provider provider) {
		try {
			Query q = em.createQuery("SELECT x FROM ProviderLocation x WHERE x.provider.id = :providerId")
				.setParameter("providerId", provider.getId());
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}

}
