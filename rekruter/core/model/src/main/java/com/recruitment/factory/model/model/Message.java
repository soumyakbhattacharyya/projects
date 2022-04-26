package com.recruitment.factory.model.model;

import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class Message {

	private String id;
	private String sender;
	private String subject;
	private String body;
	private Date sentAt;
	private Map<String, String> metadata;
}
