package com.cognizant.formit.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("node")
public class NodeResource implements Resource {

	@XStreamAsAttribute
	private String name = "PC194738";
	@XStreamAsAttribute
	private String description = "Rundeck server node";
	@XStreamAsAttribute
	private String tags = "";
	@XStreamAsAttribute
	private String hostname = "PC194738";
	@XStreamAsAttribute
	private String osArch = "amd64";
	@XStreamAsAttribute
	private String osFamily = "windows";
	@XStreamAsAttribute
	private String osName = "Windows 7";
	@XStreamAsAttribute
	private String osVersion = "6.1";
	@XStreamAsAttribute
	private String username = "12345";
	
	@XStreamAsAttribute
	private Attribute attribute= new Attribute(); 

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname
	 *            the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * @return the osArch
	 */
	public String getOsArch() {
		return osArch;
	}

	/**
	 * @param osArch
	 *            the osArch to set
	 */
	public void setOsArch(String osArch) {
		this.osArch = osArch;
	}

	/**
	 * @return the osFamily
	 */
	public String getOsFamily() {
		return osFamily;
	}

	/**
	 * @param osFamily
	 *            the osFamily to set
	 */
	public void setOsFamily(String osFamily) {
		this.osFamily = osFamily;
	}

	/**
	 * @return the osName
	 */
	public String getOsName() {
		return osName;
	}

	/**
	 * @param osName
	 *            the osName to set
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}

	/**
	 * @return the osVersion
	 */
	public String getOsVersion() {
		return osVersion;
	}

	/**
	 * @param osVersion
	 *            the osVersion to set
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	
	
	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((osArch == null) ? 0 : osArch.hashCode());
		result = prime * result
				+ ((osFamily == null) ? 0 : osFamily.hashCode());
		result = prime * result + ((osName == null) ? 0 : osName.hashCode());
		result = prime * result
				+ ((osVersion == null) ? 0 : osVersion.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NodeResource)) {
			return false;
		}
		NodeResource other = (NodeResource) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (hostname == null) {
			if (other.hostname != null) {
				return false;
			}
		} else if (!hostname.equals(other.hostname)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (osArch == null) {
			if (other.osArch != null) {
				return false;
			}
		} else if (!osArch.equals(other.osArch)) {
			return false;
		}
		if (osFamily == null) {
			if (other.osFamily != null) {
				return false;
			}
		} else if (!osFamily.equals(other.osFamily)) {
			return false;
		}
		if (osName == null) {
			if (other.osName != null) {
				return false;
			}
		} else if (!osName.equals(other.osName)) {
			return false;
		}
		if (osVersion == null) {
			if (other.osVersion != null) {
				return false;
			}
		} else if (!osVersion.equals(other.osVersion)) {
			return false;
		}
		if (tags == null) {
			if (other.tags != null) {
				return false;
			}
		} else if (!tags.equals(other.tags)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NodeResource [name=" + name + ", description=" + description
				+ ", tags=" + tags + ", hostname=" + hostname + ", osArch="
				+ osArch + ", osFamily=" + osFamily + ", osName=" + osName
				+ ", osVersion=" + osVersion + ", username=" + username + "]";
	}

}
