package org.hni.user.om;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hni.common.om.Persistable;
import org.hni.user.om.type.Gender;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents any user defined in the system. Users can play several different
 * roles such as Administrator, Treasurer, Customer, etc
 * 
 * Every User in the system MUST be associated to an Organization or Provider
 * through the mapping tables or they will have no permission within the system.
 * 
 * @author jeff3parker
 *
 */
@Entity
@Table(name = "users")
public class User implements Persistable, Serializable {
	private static final long serialVersionUID = 7553475738921092329L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "gender_code")
	private String genderCode;
	@Column(name = "mobile_phone")
	private String mobilePhone;
	@Column(name = "email")
	private String email;
	@Column(name = "deleted")
	private boolean deleted;
	@Column(name = "created")
	private Date created;

	@Column(name = "hashed_secret")
	private String hashedSecret;
	@Column(name = "salt")
	private String salt;

	private transient String token;
	private transient Long organizationId;

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	public User(String firstName, String lastName, String mobilePhone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobilePhone = mobilePhone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGenderCode() {
		return this.genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public Gender getGender() {
		return Gender.get(this.genderCode);
	}

	public void setGender(Gender gender) {
		if (null != gender) {
			this.genderCode = gender.getId();
		}
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@JsonIgnore
	public String getHashedSecret() {
		return hashedSecret;
	}

	public void setHashedSecret(String hashedSecret) {
		this.hashedSecret = hashedSecret;
	}

	@JsonIgnore
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
}