package org.hni.security.om;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

/**
 * Represents a permission (ability for a user to perform a given action). These
 * are linked to users in organizations via roles.
 * 
 *
 */
@Entity
@Table(name = "security_permissions")
public class Permission implements Persistable, Serializable {

	private static final long serialVersionUID = -5344420286199389049L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "domain") private String domain;
	@Column(name = "value") private String value;

	public Permission() {
	}

	public Permission(Long id) {
		this.id = id;
	}

	public static Permission get(Long id) {
		return new Permission(id);
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}