package com.cognizant.cloudset.core;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * JPA class representing CloudRegion The corresponding mapping table is
 * cloud_region
 */

@XmlRootElement(name = "CloudRegion")
public class CloudRegion implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String regionName;

	private CloudProvider cloudProvider;

	private Set<CloudAccount> cloudAccountss;

	private Set<CloudUser> cloudUserss;

	/**
	 * 
	 * @generated
	 */
	public CloudRegion() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * 
	 * @generated
	 */

	public void setRegionName(final String regionName) {
		this.regionName = regionName;
	}

	/**
	 * 
	 * @generated
	 */
	public String getRegionName() {
		return this.regionName;
	}

	public CloudProvider getCloudProvider() {
		return cloudProvider;
	}

	public void setCloudProvider(CloudProvider cloudProvider) {
		this.cloudProvider = cloudProvider;
	}

	/**
	 * 
	 * @generated
	 */
	@XmlTransient
	public Set<CloudAccount> getCloudAccountss() {
		return this.cloudAccountss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setCloudAccountss(final Set<CloudAccount> cloudAccountss) {
		this.cloudAccountss = cloudAccountss;
	}

	/**
	 * 
	 * @generated
	 */
	@XmlTransient
	public Set<CloudUser> getCloudUserss() {
		return this.cloudUserss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setCloudUserss(final Set<CloudUser> cloudUserss) {
		this.cloudUserss = cloudUserss;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final CloudRegion that) {
		setId(that.getId());
		setRegionName(that.getRegionName());
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
		buffer.append("regionName=[").append(regionName).append("] ");

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
		final CloudRegion other = (CloudRegion) obj;
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
