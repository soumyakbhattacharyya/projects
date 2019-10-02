package com.cognizant.cloudset.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "MachineDetails")
public class MachineDetails {

	private String vmName;
	private List<String> publicIPAddresses;
	private String publicdnsName;
	private String privateDNSName;

	public String getVmName() {
		return vmName;
	}

	
	public List<String> getPublicIPAddresses() {
		return publicIPAddresses;
	}


	public void setPublicIPAddresses(List<String> publicIPAddresses) {
		this.publicIPAddresses = publicIPAddresses;
	}


	public String getPublicdnsName() {
		return publicdnsName;
	}

	public String getPrivateDNSName() {
		return privateDNSName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	

	public void setPublicdnsName(String publicdnsName) {
		this.publicdnsName = publicdnsName;
	}

	public void setPrivateDNSName(String privateDNSName) {
		this.privateDNSName = privateDNSName;
	}

	@Override
	public String toString() {
		return "MachineDetails [vmName=" + vmName + ", IPAddress=" + publicIPAddresses
				+ ", publicdnsName=" + publicdnsName + ", privateDNSName="
				+ privateDNSName + "]";
	}	
		
}
