package com.cognizant.cloudset.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * JPA class representing AppEnvTaskOverview The corresponding mapping table is
 * app_env_task_overview
 */
@XmlRootElement(name = "AppEnvTaskOverview")
public class AppEnvTaskOverview implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long id;

	private String shortDesc;

	private String longDesc;

	private Long catagory;

	private AppEnvInstance appEnvInstance;

	/**
	 * 
	 * @generated
	 */
	public AppEnvTaskOverview() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @generated
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 
	 * @generated
	 */

	public void setShortDesc(final String shortDesc) {
		this.shortDesc = shortDesc;
	}

	/**
	 * 
	 * @generated
	 */
	public String getShortDesc() {
		return this.shortDesc;
	}

	/**
	 * 
	 * @generated
	 */

	public void setLongDesc(final String longDesc) {
		this.longDesc = longDesc;
	}

	/**
	 * 
	 * @generated
	 */
	public String getLongDesc() {
		return this.longDesc;
	}

	/**
	 * 
	 * @generated
	 */
	@XmlTransient
	public AppEnvInstance getAppEnvInstance() {
		return this.appEnvInstance;
	}

	/**
	 * 
	 * @generated
	 */
	public void setAppEnvInstance(final AppEnvInstance appEnvInstance) {
		this.appEnvInstance = appEnvInstance;

	}

	public Long getCatagory() {
		return catagory;
	}

	public void setCatagory(Long catagory) {
		this.catagory = catagory;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final AppEnvTaskOverview that) {
		setId(that.getId());
		setShortDesc(that.getShortDesc());
		setLongDesc(that.getLongDesc());
	}

	/**
	 * @generated
	 * 
	 */
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("shortDesc=[").append(shortDesc).append("] ");
		buffer.append("longDesc=[").append(longDesc).append("] ");

		return buffer.toString();
	}

	/**
	 * @generated
	 * 
	 */
	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AppEnvTaskOverview other = (AppEnvTaskOverview) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
