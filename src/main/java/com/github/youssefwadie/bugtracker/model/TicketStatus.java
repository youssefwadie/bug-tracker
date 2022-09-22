package com.github.youssefwadie.bugtracker.model;

import lombok.Getter;

@Getter
public enum TicketStatus {
	NEW("New"),
	OPEN("Open"),
	IN_PROGRESS("In Progress"),
	RESOLVED("Resolved");
	private final String description;

	TicketStatus(String description) {
		this.description = description;
	}
}
