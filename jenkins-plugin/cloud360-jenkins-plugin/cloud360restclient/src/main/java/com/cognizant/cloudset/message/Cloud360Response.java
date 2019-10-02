package com.cognizant.cloudset.message;

import com.cognizant.jpaas2.commons.expection.PlatformException;

public class Cloud360Response {
	
	private int responseCode;
	private Object responseResult;
	private PlatformException errorObject;
	/**
	 * @return the responseCode
	 */
	public int getResponseCode() {
		return responseCode;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	/**
	 * @return the responseResult
	 */
	public Object getResponseResult() {
		return responseResult;
	}
	/**
	 * @param responseResult the responseResult to set
	 */
	public void setResponseResult(Object responseResult) {
		this.responseResult = responseResult;
	}
	/**
	 * @return the errorObject
	 */
	public PlatformException getErrorObject() {
		return errorObject;
	}
	/**
	 * @param errorObject the errorObject to set
	 */
	public void setErrorObject(PlatformException errorObject) {
		this.errorObject = errorObject;
	}
	
	

}
