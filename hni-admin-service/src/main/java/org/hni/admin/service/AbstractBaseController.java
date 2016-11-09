package org.hni.admin.service;

import org.apache.shiro.SecurityUtils;

public class AbstractBaseController {

    protected boolean isPermitted(String domain, String permission, Long id) {
    	if ( SecurityUtils.getSubject().isPermitted(createPermission(domain,permission,id))) {
    		return true;
    	}
    	return false;
    	
    }

    private String createPermission(String domain, String action, Long instance)
    {
    	return String.format("%s:%s:%d", domain, action, instance);
    }

}
