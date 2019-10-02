package com.cognizant.formit.model;

import java.util.Map;

public class RNode {

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 89 * hash + (this.hostname != null ? this.hostname.hashCode() : 0);
        hash = 89 * hash + (this.username != null ? this.username.hashCode() : 0);
        hash = 89 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 89 * hash + (this.osName != null ? this.osName.hashCode() : 0);
        hash = 89 * hash + (this.osFamily != null ? this.osFamily.hashCode() : 0);
        hash = 89 * hash + (this.osArch != null ? this.osArch.hashCode() : 0);
        hash = 89 * hash + (this.osVersion != null ? this.osVersion.hashCode() : 0);
        hash = 89 * hash + (this.tags != null ? this.tags.hashCode() : 0);
        hash = 89 * hash + (this.attribute != null ? this.attribute.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RNode other = (RNode) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.hostname == null) ? (other.hostname != null) : !this.hostname.equals(other.hostname)) {
            return false;
        }
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if ((this.osName == null) ? (other.osName != null) : !this.osName.equals(other.osName)) {
            return false;
        }
        if ((this.osFamily == null) ? (other.osFamily != null) : !this.osFamily.equals(other.osFamily)) {
            return false;
        }
        if ((this.osArch == null) ? (other.osArch != null) : !this.osArch.equals(other.osArch)) {
            return false;
        }
        if ((this.osVersion == null) ? (other.osVersion != null) : !this.osVersion.equals(other.osVersion)) {
            return false;
        }
        if ((this.tags == null) ? (other.tags != null) : !this.tags.equals(other.tags)) {
            return false;
        }
        if ((this.attribute == null) ? (other.attribute != null) : !this.attribute.equals(other.attribute)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RNode{" + "name=" + name + ", hostname=" + hostname + ", username=" + username + ", description=" + description + ", osName=" + osName + ", osFamily=" + osFamily + ", osArch=" + osArch + ", osVersion=" + osVersion + ", tags=" + tags + ", attribute=" + attribute + '}';
    }

    /* nodename - unique identifier to be assigned by the node creator
     * note : a node within the context of a rundeck project whould be unique
     * from others
     */
    private String name;
    /* hostname - IP address:ssh port to connect to the node viz. 10.225.889.66:22*/
    private String hostname;
    /* username - SSH username to connect to the node */
    private String username = "ubuntu";
    /* description - textual description which should ideally express the purpose*/
    private String description = "Deployment Slave";
    /* osName - OS name */
    private String osName = "Ubuntu";
    /* osFamily - OS family: unix, windows, cygwin. */
    private String osFamily = "Unix";
    /* osArch - OS architecture */
    private String osArch = "x86_64";
    /* osVersion - OS version */
    private String osVersion = "Ubuntu-10.04.3-LTS-(lucid)";
   
    private String attribute; 
    /* tags - set of labels for organization */
    private String tags;

    public RNode() {
    }

    public RNode(String nodename, String hostname, String username,
            String description, String osName, String osFamily, String osArch,
            String osVersion, String tags,String attributes) {
        super();
        this.name = name;
        this.hostname = hostname;
        this.username = username;
        this.description = description;
        this.osName = osName;
        this.osFamily = osFamily;
        this.osArch = osArch;
        this.osVersion = osVersion;
        this.tags = tags;
        this.attribute = attributes;
    }

    public RNode(String name, String hostname, String description, String tags,String attribute) {
        super();
        this.name = name;
        this.hostname = hostname;
        this.description = description;
        this.tags = tags;
        this.attribute = attribute;
    }

    /* Get new instance with all default attributes*/
    public static RNode _new() {
        return new RNode();
    }

    /*
     * Get a new node with bare minimum information 
     */
    public static RNode _new(String name, String hostname, String description, String tags,String attribute) {
        return new RNode(name, hostname, description, tags,attribute);
    }

    /* Get new instance by assigning all attributes explicitely */
    public static RNode _new(String nodename, String hostname, String username,
            String description, String osName, String osFamily, String osArch,
            String osVersion, String tags,String attribute) {
        return new RNode(nodename, hostname, username, description, osName,
                osFamily, osArch, osVersion, tags,attribute);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsFamily() {
        return osFamily;
    }

    public void setOsFamily(String osFamily) {
        this.osFamily = osFamily;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public RNode withTag(String tag) {
        this.setTags(tag);
        return this;
    }
    public RNode withName(String name) {
        this.setName(name);
        return this;
    }
    public RNode withHostname(String hostname) {
        this.setHostname(hostname+":"+sshPort());
        return this;
    }
    public RNode withDescription(String description) {
        this.setDescription(description);
        return this;
    }         

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	protected String sshPort(){return "22";}
}
