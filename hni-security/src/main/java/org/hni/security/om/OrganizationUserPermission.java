package org.hni.security.om;

import java.io.Serializable;

public class OrganizationUserPermission implements Serializable {
	/**
	 * guid
	 */
	private static final long serialVersionUID = -6914706948758886883L;
	private Long organizationId;
	private Long userId;
	private String permissionDomain;
	private String permission;
	private String permissionInstance;
	private String permissionName;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionDomain() {
		return permissionDomain;
	}

	public void setPermissionDomain(String permissionDomain) {
		this.permissionDomain = permissionDomain;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermissionInstance() {
		return permissionInstance;
	}

	public void setPermissionInstance(String permissionInstance) {
		this.permissionInstance = permissionInstance;
	}

}
