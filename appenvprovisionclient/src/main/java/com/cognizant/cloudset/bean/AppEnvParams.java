package com.cognizant.cloudset.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * JPA class representing AppEnvParams The corresponding mapping table is
 * app_env_params
 */
@XmlRootElement(name = "AppEnvParams")
public class AppEnvParams implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;


        //@DataBoundConstructor
        public AppEnvParams(String paramName,String paramValue){
            this.paramName = paramName;
            this.paramValue = paramValue;                    
        }
        
	private Long id;
	private String paramName;
	private String paramValue;
	private Boolean showInUi;
	private AppEnvMaster appEnvMaster;

	/**
	 * 
	 * @generated
	 */
	public AppEnvParams() {
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

	public void setParamName(final String paramName) {
		this.paramName = paramName;
	}

	/**
	 * 
	 * @generated
	 */
	public String getParamName() {
            String paramName = this.paramName;    
//            if(paramName.contains(".")){
//                    paramName = paramName.replaceAll("\\.", " ");                    
//                }	
            return paramName;
	}

	/**
	 * 
	 * @generated
	 */

	public void setParamValue(final String paramValue) {
		this.paramValue = paramValue;
	}

	/**
	 * 
	 * @generated
	 */
	public String getParamValue() {
		return this.paramValue;
	}

	/**
	 * 
	 * @generated
	 */
	@XmlTransient
	public AppEnvMaster getAppEnvMaster() {
		return this.appEnvMaster;
	}

	/**
	 * 
	 * @generated
	 */
	public void setAppEnvMaster(final AppEnvMaster appEnvMaster) {
		this.appEnvMaster = appEnvMaster;

	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final AppEnvParams that) {
		setId(that.getId());
		setParamName(that.getParamName());
		setParamValue(that.getParamValue());
	}

	public Boolean getShowInUi() {
		return showInUi;
	}

	public void setShowInUi(Boolean showInUi) {
		this.showInUi = showInUi;
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
		buffer.append("paramName=[").append(paramName).append("] ");
		buffer.append("paramValue=[").append(paramValue).append("] ");

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
		final AppEnvParams other = (AppEnvParams) obj;
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
