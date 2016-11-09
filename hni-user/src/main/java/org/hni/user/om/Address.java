package org.hni.user.om;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

/**
 * A physical or mailing address.  This object is used and mapped by various 
 * other entities in the system such as organization, provider, providerLocation, etc
 * 
 *
 */
@Entity
@Table(name="addresses")
public class Address implements Persistable, Serializable {

	private static final long serialVersionUID = 435871577597384034L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Basic private String name;
	@Column(name="address_line1") private String address1;
	@Column(name="address_line2") private String address2;
	@Basic private String city;
	@Basic private String state;
	@Basic private String zip;
	@Basic private String timezone;
	@Column(name="longitude") private String longitude;
	@Column(name="latitude") private String latitude;
	
	public Address() {}
	public Address(Long id) {
		this.id = id;
	}
	public Address(String address1, String address2, String city, String state, String zip) {
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	
}
