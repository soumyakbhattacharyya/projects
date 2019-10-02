package com.cognizant.cloudset.command;

public enum Command {

	GET("GET"), POST("POST"), GET_COLLECTION("GET_COLLECTION"), DELETE("DELETE");

	String value;

	Command(String value) {
		this.value = value;
	}
}
