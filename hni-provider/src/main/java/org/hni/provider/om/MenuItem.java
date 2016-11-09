package org.hni.provider.om;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents an item on a menu.  A menuItem exists for a single
 * menu.  The price is estimated and may or may not be populated.
 *
 */
@Entity
@Table(name="menu_items")
public class MenuItem implements Persistable, Serializable {

	private static final long serialVersionUID = 7668779194229938112L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	
	@Column(name="name") private String name;
	@Column(name="description") private String description;
	@Column(name="price") private Double price;
	@Column(name="expires") private Date expires;
	
	@ManyToOne
	@JoinColumn(name="menu_id", referencedColumnName = "id")
	private Menu menu;	

	public MenuItem() {}
	public MenuItem(Long id) {
		this.id = id;
	}
	public MenuItem(String name, String description, Double price, Date expires) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.expires = expires;
	}
	
	@Override
	public Object getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
