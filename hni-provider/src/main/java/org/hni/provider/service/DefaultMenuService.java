package org.hni.provider.service;

import java.util.Collection;

import javax.inject.Inject;

import org.hni.common.service.AbstractService;
import org.hni.provider.dao.MenuDAO;
import org.hni.provider.om.Menu;
import org.hni.provider.om.Provider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultMenuService extends AbstractService<Menu> implements MenuService {

	private MenuDAO menuDao;
	
	@Inject
	public DefaultMenuService(MenuDAO menuDao) {
		super(menuDao);
		this.menuDao = menuDao;
	}

	@Override
	public Collection<Menu> with(Provider provider) {
		return menuDao.get(provider);
	}



}
