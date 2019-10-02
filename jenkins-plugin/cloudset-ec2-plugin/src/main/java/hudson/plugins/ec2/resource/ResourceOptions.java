package hudson.plugins.ec2.resource;

import hudson.plugins.ec2.EC2PrivateKey;

/**
 *  this class contains all the configuration details
 *  for Amazon Account and Amazon machine resource details. 
 *   
 *   
 * @author CloudSet Team
 *
 */

public class ResourceOptions {

	// cloud account number
	private String accountNumber;
	// cloud publicCredential
	private String publicCredential;
	// cloud private credentials
	private String privateCredential;
	// cloud providerID (e.g 02 for aws)
	private String providerID;
	// clud type e.g. aws
	private String cloudType;
	// cloud end point
	private String endPoint;
	// cloud image id
	private String imageId;
	// cloud imageType MICRO or MEDIUM etc
	private String imageType;
	// cloud data center id
	private String dataCenterId;
	// cloud subnetid
	private String subnetID;
	// minimum instance to launch
	private int minInstance;
	// maximum instance to launch
	private int maxInstance;
	// aws key name to be used to connect the launched machine
	private String key;
	// security Group
	private String securityGroup;
	// vm login id
	private String vmLoginUserID;	
	// cloudset project User id
	private String projectUserId;
	// cloudset project id
	private String projectID;	
	// kernel-id
	private String kernelID;
	// EC2PrivateKey privateKey
	private EC2PrivateKey privateKey;
	// is existing instance
	private boolean isExistingInstance;
	// slave Label which is to be used for job execution
	private String slaveLabel;
	// start the machine when job starts and stop when job completes
	private boolean startStopMachine;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getPublicCredential() {
		return publicCredential;
	}
	public void setPublicCredential(String publicCredential) {
		this.publicCredential = publicCredential;
	}
	public String getPrivateCredential() {
		return privateCredential;
	}
	public void setPrivateCredential(String privateCredential) {
		this.privateCredential = privateCredential;
	}
	public String getProviderID() {
		return providerID;
	}
	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getDataCenterId() {
		return dataCenterId;
	}
	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}
	public String getSubnetID() {
		return subnetID;
	}
	public void setSubnetID(String subnetID) {
		this.subnetID = subnetID;
	}
	public int getMinInstance() {
		return minInstance;
	}
	public void setMinInstance(int minInstance) {
		this.minInstance = minInstance;
	}
	public int getMaxInstance() {
		return maxInstance;
	}
	public void setMaxInstance(int maxInstance) {
		this.maxInstance = maxInstance;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSecurityGroup() {
		return securityGroup;
	}
	public void setSecurityGroup(String securityGroup) {
		this.securityGroup = securityGroup;
	}
	public String getVmLoginUserID() {
		return vmLoginUserID;
	}
	public void setVmLoginUserID(String vmLoginUserID) {
		this.vmLoginUserID = vmLoginUserID;
	}
	public String getProjectUserId() {
		return projectUserId;
	}
	public void setProjectUserId(String projectUserId) {
		this.projectUserId = projectUserId;
	}
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getKernelID() {
		return kernelID;
	}
	public void setKernelID(String kernelID) {
		this.kernelID = kernelID;
	}
	public EC2PrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(EC2PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	public boolean isExistingInstance() {
		return isExistingInstance;
	}
	public void setExistingInstance(boolean isExistingInstance) {
		this.isExistingInstance = isExistingInstance;
	}
	public String getSlaveLabel() {
		return slaveLabel;
	}
	public void setSlaveLabel(String slaveLabel) {
		this.slaveLabel = slaveLabel;
	}
	public String getCloudType() {
		return cloudType;
	}
	public void setCloudType(String cloudType) {
		this.cloudType = cloudType;
	}
	public boolean isStartStopMachine() {
		return startStopMachine;
	}
	public void setStartStopMachine(boolean startStopMachine) {
		this.startStopMachine = startStopMachine;
	}
	
	

}
