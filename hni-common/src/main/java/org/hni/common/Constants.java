package org.hni.common;

public interface Constants {
	static final Long SUPER_USER = 1L;
	static final Long ADMIN = 2L;
	static final Long USER = 5L;  // e.g. volunteers
	static final Long CLIENT = 7L;
	
	// domains
	static final String ORGANIZATION = "organization";
	static final String PROVIDER = "provider";
	static final String ORDER = "order";
	
	// basic permissions
	static final String CREATE = "create";
	static final String READ = "read";
	static final String UPDATE = "update";
	static final String DELETE = "delete";
	
	static final String USERID = "userId";
	static final String PERMISSIONS = "permissions";

}
