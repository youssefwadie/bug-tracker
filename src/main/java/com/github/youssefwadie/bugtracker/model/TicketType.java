package com.github.youssefwadie.bugtracker.model;

import lombok.Getter;

@Getter
public enum TicketType {
	BUG_OR_ERROR("Bugs/Errors"),
	FEATURE_REQUESTS("Feature Requests"),
	TRAINING_OR_DOCUMENT_REQUEST("Training/Document Requests");
	private final String description;

	TicketType(String description) {
		this.description = description;
	}
}
