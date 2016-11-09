package org.hni.user.dao;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.Address;
import org.springframework.stereotype.Component;

@Component
public class DefaultAddressDAO extends AbstractDAO<Address> implements AddressDAO {

	protected DefaultAddressDAO() {
		super(Address.class);
	}



}
