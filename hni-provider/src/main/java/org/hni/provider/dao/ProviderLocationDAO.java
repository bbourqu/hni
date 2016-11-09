package org.hni.provider.dao;

import java.util.Collection;

import org.hni.common.dao.BaseDAO;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;

public interface ProviderLocationDAO extends BaseDAO<ProviderLocation> {

	Collection<ProviderLocation> with(Provider provider);

}
