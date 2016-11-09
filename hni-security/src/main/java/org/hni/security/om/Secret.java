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
@Table(name = "secrets")
public class Secret implements Persistable, Serializable {
	private static final long serialVersionUID = -5344420286199389049L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "secret")
	private String secret;

	public Secret() {
	}

	public Secret(Long id) {
		this.id = id;
	}

	public static Secret get(Long id) {
		return new Secret(id);
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}