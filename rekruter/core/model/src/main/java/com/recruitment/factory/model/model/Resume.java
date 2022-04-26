package com.recruitment.factory.model.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.recruitment.factory.model.vacancy.Opening;

import lombok.Data;

@Data
public class Resume {
	
	private String fileName;
	private String content;
	private String downloadLocation;
	
	private Map<String, String> profile = Collections.synchronizedMap(new HashMap<String, String>());
	
	private String source;// from which site this candidate was discovered
	private String sourceType; // API or website 
	
	private String assignedTo;
	private Opening opening;
	private Stage stage;
	
	

}
