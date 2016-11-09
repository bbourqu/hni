package org.hni.provider.service;

import java.util.Collection;

import javax.inject.Inject;

import org.hni.common.service.AbstractService;
import org.hni.provider.dao.ProviderLocationDAO;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultProviderLocationService extends AbstractService<ProviderLocation> implements ProviderLocationService {

	private ProviderLocationDAO providerLocationDao;
	
	@Inject
	public DefaultProviderLocationService(ProviderLocationDAO providerLocationDao) {
		super(providerLocationDao);
		this.providerLocationDao = providerLocationDao;
	}

	@Override
	public Collection<ProviderLocation> with(Provider provider) {
		return this.providerLocationDao.with(provider);
	}


}
