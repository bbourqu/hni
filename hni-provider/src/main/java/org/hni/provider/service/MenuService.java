package org.hni.provider.service;

import java.util.Collection;

import org.hni.common.service.BaseService;
import org.hni.provider.om.Menu;
import org.hni.provider.om.Provider;

public interface MenuService extends BaseService<Menu> {

	/**
	 * Return the menus for the provider
	 * @param provider
	 * @return
	 */
	Collection<Menu> with(Provider provider);
}
