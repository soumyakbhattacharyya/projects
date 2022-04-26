package com.recruitment.factory.model.model;

import java.util.List;

import lombok.Data;

@Data
public class Evaluation {
	
	private String id;
	private List<Feedback> feedback;

}
