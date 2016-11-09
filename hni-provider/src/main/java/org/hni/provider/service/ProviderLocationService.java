package org.hni.provider.service;

import java.util.Collection;

import org.hni.common.service.BaseService;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;

public interface ProviderLocationService extends BaseService<ProviderLocation> {

	Collection<ProviderLocation> with(Provider provider);
}
