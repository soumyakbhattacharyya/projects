package com.cognizant.cloudset.bean;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * JPA class representing AppEnvMaster The corresponding mapping table is
 * app_env_master
 */

@XmlRootElement(name = "AppEnvMaster")
public class AppEnvMaster implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @generated
	 */
	private Long id;

	/**
	 * 
	 * @generated
	 */
	private Long version;

	/**
	 * 
	 * @generated
	 */
	private String shortDesc;

	/**
	 * 
	 * @generated
	 */
	private String longDesc;

	/**
	 * 
	 * @generated
	 */

	private Integer providerId;

	/**
	 * 
	 * @generated
	 */

	private Integer cloudAccountId;

	private Set<AppEnvInstance> appEnvInstancess;

	private Set<AppEnvParams> appEnvParamsess;

	/**
	 * 
	 * @generated
	 */
	public AppEnvMaster() {
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

	public void setVersion(final Long version) {
		this.version = version;
	}

	/**
	 * 
	 * @generated
	 */
	public Long getVersion() {
		return this.version;
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

	public void setProviderId(final Integer providerId) {
		this.providerId = providerId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getProviderId() {
		return this.providerId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setCloudAccountId(final Integer cloudAccountId) {
		this.cloudAccountId = cloudAccountId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getCloudAccountId() {
		return this.cloudAccountId;
	}

	/**
	 * 
	 * @generated
	 */
	@XmlTransient
	public Set<AppEnvInstance> getAppEnvInstancess() {
		return this.appEnvInstancess;
	}

	/**
	 * 
	 * @generated
	 */
	public void setAppEnvInstancess(final Set<AppEnvInstance> appEnvInstancess) {
		this.appEnvInstancess = appEnvInstancess;
	}

	/**
	 * 
	 * @generated
	 */
	@XmlTransient
	public Set<AppEnvParams> getAppEnvParamsess() {
		return this.appEnvParamsess;
	}

	/**
	 * 
	 * @generated
	 */
	public void setAppEnvParamsess(final Set<AppEnvParams> appEnvParamsess) {
		this.appEnvParamsess = appEnvParamsess;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final AppEnvMaster that) {
		setId(that.getId());
		setVersion(that.getVersion());
		setShortDesc(that.getShortDesc());
		setLongDesc(that.getLongDesc());
		setProviderId(that.getProviderId());
		setCloudAccountId(that.getCloudAccountId());
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
		buffer.append("\n");
		buffer.append("version=[").append(version).append("] ");
		buffer.append("\n");
		buffer.append("shortDesc=[").append(shortDesc).append("] ");
		buffer.append("\n");
		buffer.append("longDesc=[").append(longDesc).append("] ");
		buffer.append("\n");
		buffer.append("providerId=[").append(providerId).append("] ");
		buffer.append("\n");
		buffer.append("cloudAccountId=[").append(cloudAccountId).append("] ");

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
		final AppEnvMaster other = (AppEnvMaster) obj;
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
