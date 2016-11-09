package org.hni.security.om;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

/**
 * Represents a permission (ability for a user to perform a given action). These
 * are linked to users in organizations via roles.
 */
@Entity
@Table(name = "security_role_permissions")
public class RolePermission implements Persistable, Serializable {
	/*
	 * TODO: later ieration can be concerned about who changed which role
	 * permission, and when.
	 */
	private static final long serialVersionUID = -5344420286199389049L;
	@EmbeddedId
	private RolePermissionPK id;

	@Column(name="all_instances") private boolean allInstances;
	
	public RolePermission() {
	}

	public RolePermission(Long roleId, Long permissionId) {
		this(new RolePermissionPK(roleId, permissionId));
	}

	public RolePermission(RolePermissionPK id) {
		this.id = id;
	}

	@Override
	public RolePermissionPK getId() {
		return id;
	}

	public void setId(RolePermissionPK id) {
		this.id = id;
	}

	public boolean isAllInstances() {
		return allInstances;
	}

	public void setAllInstances(boolean allInstances) {
		this.allInstances = allInstances;
	}
	
	
}